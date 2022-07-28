package com.dionkn.githubuserapp.Model.Adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dionkn.githubuserapp.Fragment.FollowersFragment
import com.dionkn.githubuserapp.Fragment.FollowingFragment
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse

class DetailFragmentAdapter(activity: AppCompatActivity, userGithubResponse: UserGithubResponse): FragmentStateAdapter(activity) {
    private val deliveredUsername = userGithubResponse.login
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment ?= null
        when(position){
            0 -> fragment = FollowersFragment.newInstance(deliveredUsername)
            1 -> fragment = FollowingFragment.newInstance(deliveredUsername)
        }
        return fragment as Fragment
    }
}