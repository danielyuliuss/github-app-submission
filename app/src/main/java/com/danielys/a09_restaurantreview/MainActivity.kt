package com.danielys.a09_restaurantreview

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielys.a09_restaurantreview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mUserPreference: UserPreference
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mUserPreference = UserPreference(this)
        getDarkLight()

        mainViewModel.listgithubusers.observe(this) { usersGithub ->
            if (usersGithub != null) {
                setUserGithubData(usersGithub)
            }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.favorite)
        {
            val intent = Intent(this,FavoritActivity::class.java)
            startActivity(intent)
        }
        else if (item.itemId == R.id.darklight)
        {
            if(ISLIGHT)
            {
                mUserPreference.setDaylightSet(false)
                item.icon = (ContextCompat.getDrawable(this, R.drawable.ic_dark_mode_24))
                Toast.makeText(this, "turning dark", Toast.LENGTH_SHORT).show()
            }
            else
            {
                mUserPreference.setDaylightSet(true)
                item.icon = (ContextCompat.getDrawable(this, R.drawable.ic_light_mode_24))
                Toast.makeText(this, "turning light", Toast.LENGTH_SHORT).show()
            }
            getDarkLight()
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)


        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getUserGithub(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    private fun setUserGithubData(usersGithub: List<ItemsItem>) {
        val adapter = GithubUserAdapter(usersGithub, this)
        binding.rvUser.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun getDarkLight(){
        ISLIGHT = mUserPreference.getDaylightSet()
        if(ISLIGHT)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }


    companion object {
        private var ISLIGHT = true
    }
}