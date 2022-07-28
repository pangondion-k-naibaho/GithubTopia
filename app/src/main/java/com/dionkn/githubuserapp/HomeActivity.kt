package com.dionkn.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dionkn.githubuserapp.Model.Adapter.ListGithubUsersAdapter
import com.dionkn.githubuserapp.Model.Class.GithubUser
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.ViewModel.HomeViewModel
import com.dionkn.githubuserapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val TAG = HomeActivity::class.java.simpleName
    private lateinit var binding : ActivityHomeBinding
    private val listUsers = ArrayList<GithubUser>()
    private var EXTRA_GITHUB_USER = "EXTRA_GITHUB_USER"
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeRvusers.setHasFixedSize(true)
        binding.homeRvusers.layoutManager = LinearLayoutManager(this)

        homeViewModel.getUserGithub()
        homeViewModel.listUserGithub.observe(this,{ listUsers ->
            setUserData(listUsers)
        })

    }

    private fun setUserData(userData: List<UserGithubResponse>){
        val arrListUserData = ArrayList<UserGithubResponse>()
        userData.forEach {
            arrListUserData.addAll(listOf(it))
        }
        val adapter = ListGithubUsersAdapter(arrListUserData, object: ListGithubUsersAdapter.ItemListener{
            override fun onItemClicked(item: UserGithubResponse) {
                Log.d(TAG, "item : $item")
                showDetailUser(item.login)
            }

        })

        binding.homeRvusers.adapter = adapter

    }

    private fun showDetailUser(username: String){
        startActivity(
            DetailActivity.newIntent(this, username).apply {
                putExtra(EXTRA_GITHUB_USER, username)
            }
        )
    }

}