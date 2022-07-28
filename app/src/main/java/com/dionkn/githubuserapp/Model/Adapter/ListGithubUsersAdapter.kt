package com.dionkn.githubuserapp.Model.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dionkn.githubuserapp.Model.Class.GithubUser
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.R
import com.dionkn.githubuserapp.databinding.RvItemUserBinding

class ListGithubUsersAdapter(
    var data : ArrayList<UserGithubResponse>,
    private val listener: ItemListener
) : RecyclerView.Adapter<ListGithubUsersAdapter.UserItemHolder>()
{
    interface ItemListener{
        fun onItemClicked(item: UserGithubResponse)
    }

    inner class UserItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: UserGithubResponse, listener: ItemListener) = with(itemView){
            val binding = RvItemUserBinding.bind(itemView)
            binding.apply {
                tvItemuserFullname.text = item.login
                val followers = "${item.followers} followers • ${item.following} following"
                tvItemuserCountfollower.text = followers

                Glide.with(context).load(item.avatar_url)
                    .into(ivItemuserPic)
            }
            binding.root.setOnClickListener {
                item?.let { it -> listener.onItemClicked(it) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_user, parent, false)
        return UserItemHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemCount(): Int = data.size

}