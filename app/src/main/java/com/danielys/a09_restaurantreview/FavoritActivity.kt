package com.danielys.a09_restaurantreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielys.a09_restaurantreview.database.User
import com.danielys.a09_restaurantreview.databinding.ActivityFavoritBinding
import com.danielys.a09_restaurantreview.databinding.ActivityMainBinding
import com.danielys.a09_restaurantreview.helper.ViewModelFactory

class FavoritActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var listFavorit : List<User>

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager


        val dbViewModel = obtainViewModel(this@FavoritActivity)
        dbViewModel.getAllFavorite().observe(this) { mlistFavorit ->
            if (mlistFavorit != null) {
                listFavorit = mlistFavorit
                setUserGithubData(listFavorit)
            }
        }


    }

    private fun obtainViewModel(activity: AppCompatActivity): DbViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DbViewModel::class.java)
    }

    private fun setUserGithubData(usersGithub: List<User>) {
        val adapter = FavoritAdapter(usersGithub, this)
        binding.rvUser.adapter = adapter
    }
}