package com.danielys.a09_restaurantreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFollowingFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFollowingFragment.ARG_SECTION_NUMBER, position + 1)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}