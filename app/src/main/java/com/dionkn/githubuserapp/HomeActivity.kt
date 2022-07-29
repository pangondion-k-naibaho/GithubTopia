package com.dionkn.githubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
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
    private var arrListUserData= ArrayList<UserGithubResponse>()
    private var arrListSearchedUserData= ArrayList<UserGithubResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeAllRvusers.setHasFixedSize(true)
        binding.homeAllRvusers.layoutManager = LinearLayoutManager(this)

        binding.homeSearchRvusers.setHasFixedSize(true)
        binding.homeSearchRvusers.layoutManager = LinearLayoutManager(this)

        homeViewModel.getUserGithub()
        homeViewModel.listUserGithub.observe(this,{ listUsers ->
            setUserData(listUsers)
        })

        searchBarSetUp()

    }

    private fun setUserData(userData: List<UserGithubResponse>){
        userData.forEach {
            arrListUserData.addAll(listOf(it))
        }
        val adapter = ListGithubUsersAdapter(arrListUserData, object: ListGithubUsersAdapter.ItemListener{
            override fun onItemClicked(item: UserGithubResponse) {
                Log.d(TAG, "item : $item")
                showDetailUser(item.login)
            }

        })

        binding.homeAllRvusers.adapter = adapter

    }

    private fun showDetailUser(username: String){
        startActivity(
            DetailActivity.newIntent(this, username).apply {
                putExtra(EXTRA_GITHUB_USER, username)
            }
        )
    }

    private fun searchBarSetUp(){
        with(binding){
            etSearchHome.addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if(etSearchHome.text.toString().length > 0){
                        ivClearHome.visibility = View.VISIBLE
                    }else ivClearHome.visibility = View.GONE
                }

            })

            ivClearHome.setOnClickListener {
                etSearchHome.text.clear()
                when(homeSearchRvusers.visibility == View.VISIBLE){
                    true -> {
                        homeSearchRvusers.visibility = View.GONE
                        homeAllRvusers.visibility = View.VISIBLE
                    }
                }
            }

            ivSearchHome.setOnClickListener {
                Log.d(TAG, "Search : ${etSearchHome.text.toString().toLowerCase()}")
                userSearch(etSearchHome.text.toString().toLowerCase())
            }
        }

    }

    private fun userSearch(text: String){
        binding.homeAllRvusers.visibility = View.GONE
        binding.homeSearchRvusers.visibility = View.VISIBLE
        homeViewModel.getSearchedUser(text)
        homeViewModel.listSearchedUser.observe(this, { listSearchedUser ->
            Log.d(TAG, "listSearchedUser : ${listSearchedUser.toString()}")
            setSearchedUserData(listSearchedUser)
        })
    }

    private fun setSearchedUserData(userData: List<UserGithubResponse>){
        userData.forEach {
            arrListSearchedUserData.addAll(listOf(it))
        }

        val adapter = ListGithubUsersAdapter(arrListSearchedUserData, object: ListGithubUsersAdapter.ItemListener{
            override fun onItemClicked(item: UserGithubResponse) {
                Log.d(TAG, "item : $item")
                showDetailUser(item.login)
            }
        })

        binding.homeSearchRvusers.adapter = adapter
    }

}