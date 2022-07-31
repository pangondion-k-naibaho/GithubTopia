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
    var data : ArrayList<UserGithubResponse> ?= null,
    private val listener: ItemListener ?= null
) : RecyclerView.Adapter<ListGithubUsersAdapter.UserItemHolder>()
{
    interface ItemListener{
        fun onItemClicked(item: UserGithubResponse){}
    }

    inner class UserItemHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: UserGithubResponse, listener: ItemListener) = with(itemView){
            val binding = RvItemUserBinding.bind(itemView)
            binding.apply {
                tvItemuserFullname.text = item.login
                val followers = "id ${item.id} • node_id ${maskLastDigits(item.node_id, 5, "*")}"
                tvItemuserCountfollower.text = followers

                Glide.with(context).load(item.avatar_url)
                    .into(ivItemuserPic)
            }
            binding.root.setOnClickListener {
                item?.let { it -> listener.onItemClicked(it) }
            }
        }
    }

    fun maskLastDigits(inputStr: String?, numberOfMask: Int, symbol: String):String{
        var mask = ""
        if(inputStr == null) return "-"
        for (x in 0 until numberOfMask) mask += symbol
        return inputStr.substring(0, numberOfMask-1) + mask
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_user, parent, false)
        return UserItemHolder(view)
    }

    override fun onBindViewHolder(holder: UserItemHolder, position: Int) {
        holder.bind(data?.get(position)!!, listener!!)
    }

    override fun getItemCount(): Int = data?.size!!

}