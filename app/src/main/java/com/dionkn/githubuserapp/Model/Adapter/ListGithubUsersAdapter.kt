package com.dionkn.githubuserapp.Model.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dionkn.githubuserapp.Model.Class.GithubUser
import com.dionkn.githubuserapp.databinding.RvItemUserBinding

class ListGithubUsersAdapter(private val listUser: ArrayList<GithubUser>) : RecyclerView.Adapter<ListGithubUsersAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback : OnItemClickCallback
    lateinit var binding: RvItemUserBinding
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.cvUsers)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(fullName, userName, userBio, numberFollower, numberFollowing, urlProfilePict) = listUser[position]

        Glide.with(holder.itemView).load(urlProfilePict).into(binding.ivItemuserPic)
        binding.tvItemuserFullname.text = when(fullName){
            "" -> userName
            else -> fullName
        }
        binding.tvItemuserUsername.text = userName

        var united = "$numberFollower follower \u2022 $numberFollowing following"
        binding.tvItemuserCountfollower.text = united

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listUser[position])
        }

    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback{
        fun onItemClicked(data: GithubUser)
    }
}