package com.dionkn.githubuserapp.Model.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dionkn.githubuserapp.Model.Class.UserRepo
import com.dionkn.githubuserapp.Model.DummyData.DummyConstants.TOOL.Companion.HACK
import com.dionkn.githubuserapp.Model.DummyData.DummyConstants.TOOL.Companion.HTML
import com.dionkn.githubuserapp.Model.DummyData.DummyConstants.TOOL.Companion.JAVA
import com.dionkn.githubuserapp.Model.DummyData.DummyConstants.TOOL.Companion.JAVASCRIPT
import com.dionkn.githubuserapp.Model.DummyData.DummyConstants.TOOL.Companion.KOTLIN
import com.dionkn.githubuserapp.Model.DummyData.DummyConstants.TOOL.Companion.PHP
import com.dionkn.githubuserapp.Model.DummyData.DummyConstants.TOOL.Companion.PYTHON
import com.dionkn.githubuserapp.R
import com.dionkn.githubuserapp.databinding.RvItemRepoBinding

class ListUserRepoAdapter(private val listRepo: ArrayList<UserRepo>) : RecyclerView.Adapter<ListUserRepoAdapter.ViewHolder>(){
    lateinit var binding : RvItemRepoBinding
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RvItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.cvRepos)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val(repoName, toolUsed) = listRepo[position]
        binding.tvDetailuserReponame.text = repoName
        val drawableNeeded = when(toolUsed){
            HTML -> R.drawable.ic_tool_html
            KOTLIN -> R.drawable.ic_tool_kotlin
            JAVA -> R.drawable.ic_tool_java
            JAVASCRIPT -> R.drawable.ic_tool_javascript
            PYTHON -> R.drawable.ic_tool_python
            HACK -> R.drawable.ic_tool_hack
            PHP -> R.drawable.ic_tool_php
            else -> R.drawable.ic_tool_other
        }
        binding.tvDetailuserRepotool.apply {
            setText(toolUsed)
            setCompoundDrawablesWithIntrinsicBounds(drawableNeeded,0,0,0)
        }
    }

    override fun getItemCount(): Int = listRepo.size
}