package com.dionkn.githubuserapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.dionkn.githubuserapp.Model.Class.GithubUser
import com.dionkn.githubuserapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private var parsedGithubUser = GithubUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        initBundle()
        setDisplay()
    }

    companion object{
        const val EXTRA_GITHUB_USER = "EXTRA_GITHUB_USER"
        fun newIntent(context: Context, detailUser: GithubUser) : Intent = Intent(context, DetailActivity::class.java)
            .putExtra(EXTRA_GITHUB_USER, detailUser)
    }

    private fun setupActionBar(){
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("Detail User")
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
        parsedGithubUser = intent.getParcelableExtra(EXTRA_GITHUB_USER) ?: GithubUser()
        Log.d(this.toString(), parsedGithubUser.toString())
    }

    private fun setDisplay(){
        Glide.with(this).load(parsedGithubUser.urlProfilePict).into(binding.ivDetailuserPic)
        binding.tvDetailuserFullname.text = when(parsedGithubUser.fullName){
            "" -> parsedGithubUser.userName
            else -> parsedGithubUser.fullName
        }
        binding.tvDetailuserNickname.text = parsedGithubUser.userName
        var unitedVariable = "${parsedGithubUser.numberFollower} follower \u2022 ${parsedGithubUser.numberFollowing} following"
        binding.tvDetailuserNumberfollower.text = unitedVariable
    }
}