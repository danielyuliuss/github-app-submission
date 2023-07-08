package com.danielys.a09_restaurantreview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danielys.a09_restaurantreview.database.User
import com.danielys.a09_restaurantreview.databinding.ItemUsergithubBinding

class GithubUserAdapter(private val listGitHubUser: List<ItemsItem>, private val context: Context) :
    RecyclerView.Adapter<GithubUserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemUsergithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ListViewHolder, position: Int) {
        Glide.with(context)
            .load(listGitHubUser[position].avatarUrl)
            .into(viewHolder.binding.imgProfile)
        viewHolder.binding.tvUsername.text = listGitHubUser[position].login

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("KEYID", listGitHubUser[position].login)
            startActivity(context, intent, null)
        }
    }

    override fun getItemCount() = listGitHubUser.size


    class ListViewHolder(var binding: ItemUsergithubBinding) : RecyclerView.ViewHolder(binding.root)

}

