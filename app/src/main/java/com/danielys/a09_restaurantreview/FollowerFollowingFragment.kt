package com.danielys.a09_restaurantreview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielys.a09_restaurantreview.databinding.FragmentFollowerFollowingBinding


class FollowerFollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowerFollowingBinding
    private val mainViewModel by viewModels<MainViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowerFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)

        mainViewModel.listFollowerFollowing.observe(viewLifecycleOwner) { listFollower ->
            if (listFollower != null) {
                getListFollowersFollowing(listFollower)
            }
        }

        mainViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        if (index == 1) {
            mainViewModel.getFollowingFollowers(DetailActivity.USERNAME, true)
        } else {
            mainViewModel.getFollowingFollowers(DetailActivity.USERNAME, false)
        }

    }


    private fun getListFollowersFollowing(listFollowers: List<ItemsItem>) {


        binding.rvFollowersFollowing.setHasFixedSize(true)
        binding.rvFollowersFollowing.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowersFollowing.adapter = GithubUserAdapter(listFollowers, requireActivity())
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}