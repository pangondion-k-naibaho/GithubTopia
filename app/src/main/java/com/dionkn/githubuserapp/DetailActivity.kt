package com.dionkn.githubuserapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dionkn.githubuserapp.Model.Adapter.DetailFragmentAdapter
import com.dionkn.githubuserapp.Model.Adapter.ListUserRepoAdapter
import com.dionkn.githubuserapp.Model.Class.GithubUser
import com.dionkn.githubuserapp.Model.Class.UserRepo
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.ViewModel.DetailViewModel
import com.dionkn.githubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private val TAG = DetailActivity::class.java.simpleName
    private lateinit var binding : ActivityDetailBinding
    private lateinit var deliveredUsername : String
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        initBundle()
    }

    companion object{
        const val EXTRA_GITHUB_USER = "EXTRA_GITHUB_USER"
        fun newIntent(context: Context, usernameDelivered: String) : Intent = Intent(context, DetailActivity::class.java)
            .putExtra(EXTRA_GITHUB_USER, usernameDelivered)

        private val TAB_TITLES = arrayOf(
            "Followers",
            "Following"
        )
    }


    private fun setupActionBar(){
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("Detail User")
            actionBar.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBundle(){
        deliveredUsername = intent.getStringExtra(EXTRA_GITHUB_USER)!!
        Log.d(TAG, "deliveredUsername: ${deliveredUsername}")
        getUser(deliveredUsername)
    }

    private fun getUser(userName: String){
        detailViewModel.getDetailUser(userName)
        detailViewModel.detailUser.observe(this, { user ->
           setUpDisplay(user)
        })
    }

    private fun setUpDisplay(userGithub: UserGithubResponse){
        with(binding){
            Glide.with(this@DetailActivity).load(userGithub.avatar_url)
                .into(ivDetailuserPic)

            tvDetailuserFullname.text = userGithub.login
            tvDetailuserNickname.text = when(userGithub.name){
                null, "" -> " - "
                else -> userGithub.name
            }
            tvDetailuserCompany.text = when(userGithub.company){
                null, "" -> " - "
                else -> " ${userGithub.company} "
            }
            tvDetailuserLocation.text = when(userGithub.location){
                null, "" -> " - "
                else -> " ${userGithub.location} "
            }
        }
        val arrayTitle = arrayOf(
            "Followers (${userGithub.followers})",
            "Following (${userGithub.following})"
        )
        setupFragment(userGithub, arrayTitle)
    }

    private fun setupFragment(userGithub: UserGithubResponse, arrayTitle: Array<String>){
        val detailPagerAdapter = DetailFragmentAdapter(this, userGithub)
        binding.vpDetailUser.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tlDetailUser, binding.vpDetailUser){ tab, position ->
            tab.text = arrayTitle[position]
        }.attach()
    }
}