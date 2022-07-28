package com.dionkn.githubuserapp.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dionkn.githubuserapp.Model.Adapter.ListGithubUsersAdapter
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.R
import com.dionkn.githubuserapp.ViewModel.FollowersFollowingViewModel
import com.dionkn.githubuserapp.databinding.FragmentFollowingBinding
import java.util.ArrayList

class FollowingFragment: Fragment() {
    val TAG = FollowingFragment::class.java.simpleName

    private lateinit var binding : FragmentFollowingBinding
    private lateinit var deliveredUsername: String
    private val followersFollowingViewModel by viewModels<FollowersFollowingViewModel>()

    companion object{
        private const val DELIVERED_USERNAME = "DELIVERED_USERNAME"
        fun newInstance(deliveredUsername: String): FollowingFragment{
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(DELIVERED_USERNAME, deliveredUsername)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.bind(
            inflater.inflate(
                R.layout.fragment_following,
                container,
                false
            )
        )
        deliveredUsername = arguments?.getString(DELIVERED_USERNAME).toString()
        Log.d(TAG, "delivered Username : $deliveredUsername")

        binding.rvFragFollowing.setHasFixedSize(true)
        binding.rvFragFollowing.layoutManager = LinearLayoutManager(this.requireActivity())

        followersFollowingViewModel.getListFollowing(deliveredUsername)
        followersFollowingViewModel.listUserFollowing.observe(this.requireActivity(), { listFollowing ->
            setFollowingData(listFollowing)
        })

        return binding.root
    }

    private fun setFollowingData(dataFollowing: List<UserGithubResponse>){
        val arrListUserFollowing = ArrayList<UserGithubResponse>()
        dataFollowing.forEach {
            arrListUserFollowing.addAll(listOf(it))
        }
        val adapter = ListGithubUsersAdapter(arrListUserFollowing, object: ListGithubUsersAdapter.ItemListener{})
        binding.rvFragFollowing.adapter = adapter
    }
}