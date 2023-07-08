package com.danielys.a09_restaurantreview

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.danielys.a09_restaurantreview.database.User
import com.danielys.a09_restaurantreview.databinding.ActivityDetailBinding
import com.danielys.a09_restaurantreview.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private val TAB_TITLES = arrayOf(
            "Followers",
            "Following"
        )
        var USERNAME: String = ""
        var FAVORIT: Boolean = false
        var URLIMG :String=""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.userDetail.observe(this) { detailUser ->
            binding.tvNamaUser.text = detailUser.name
            binding.tvUsernameDetail.text = detailUser.login
            binding.tvFollowers.text =
                getString(R.string.text_follower, detailUser.followers.toString())
            binding.tvFollowing.text =
                getString(R.string.text_following, detailUser.following.toString())
            Glide.with(this)
                .load(detailUser.avatarUrl)
                .into(binding.imgProfile)
            URLIMG = detailUser.avatarUrl.toString()
        }

        USERNAME = intent.getStringExtra("KEYID").toString()
        if (USERNAME != "") {
            mainViewModel.getUserDetail(USERNAME)
        }

        val dbViewModel = obtainViewModel(this@DetailActivity)
        dbViewModel.isFavorit(USERNAME).observe(this){isFavorit ->
            FAVORIT = isFavorit
            binding.btnFavorit.setBackgroundResource(if (isFavorit) R.drawable.ic_favorite_white else R.drawable.ic_favorite_border_white )
        }

        binding.btnFavorit.setOnClickListener {
            var user:User = User(USERNAME, URLIMG)

            if(FAVORIT)
            {
                dbViewModel.delete(user)
            }
            else
            {
                dbViewModel.insert(user)
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.vpFollowerFollowing)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabsFollowerFollowing)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun obtainViewModel(activity: AppCompatActivity): DbViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DbViewModel::class.java)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}