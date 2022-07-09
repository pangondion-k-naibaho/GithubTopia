package com.dionkn.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.dionkn.githubuserapp.Model.Adapter.ListGithubUsersAdapter
import com.dionkn.githubuserapp.Model.Class.GithubUser
import com.dionkn.githubuserapp.Model.DummyData.DummyList.getGithubUser
import com.dionkn.githubuserapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private val listUsers = ArrayList<GithubUser>()
    private var EXTRA_GITHUB_USER = "EXTRA_GITHUB_USER"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeRvusers.setHasFixedSize(true)
        getGithubUser()?.forEach {
            listUsers.addAll(listOf(it))
        }
        showRecyclerView()
    }

    private fun showRecyclerView(){
        binding.homeRvusers.layoutManager = LinearLayoutManager(this)
        val listUsersAdapter = ListGithubUsersAdapter(listUsers)
        binding.homeRvusers.adapter = listUsersAdapter

        listUsersAdapter.setOnItemClickCallback(object: ListGithubUsersAdapter.OnItemClickCallback{
            override fun onItemClicked(data: GithubUser) {
                showSelectedUser(data)
            }

        })

    }

    private fun showSelectedUser(githubUser: GithubUser){
        Log.d(this.toString(), "githubUser : ${githubUser.toString()}")
        startActivity(
            DetailActivity.newIntent(this, githubUser).apply {
                putExtra(EXTRA_GITHUB_USER, githubUser)
            }
        )

    }
}