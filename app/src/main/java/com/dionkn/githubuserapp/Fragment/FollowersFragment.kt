package com.dionkn.githubuserapp.Fragment

import android.os.Bundle
import android.os.UserHandle
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
import com.dionkn.githubuserapp.databinding.FragmentFollowersBinding

class FollowersFragment: Fragment() {
    val TAG = FollowersFragment::class.java.simpleName

    private lateinit var binding : FragmentFollowersBinding
    private lateinit var deliveredUsername: String
    private val followersFollowingViewModel by viewModels<FollowersFollowingViewModel>()

    companion object{
        private const val DELIVERED_USERNAME = "DELIVERED_USERNAME"
        fun newInstance(deliveredUsername: String): FollowersFragment{
            val fragment = FollowersFragment()
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
        binding = FragmentFollowersBinding.bind(
            inflater.inflate(
                R.layout.fragment_followers,
                container,
                false
            )
        )
        deliveredUsername = arguments?.getString(DELIVERED_USERNAME).toString()
        Log.d(TAG, "delivered Username : $deliveredUsername")

        binding.rvFragFollowers.setHasFixedSize(true)
        binding.rvFragFollowers.layoutManager = LinearLayoutManager(this.requireActivity())

        followersFollowingViewModel.getListFollowers(deliveredUsername)
        followersFollowingViewModel.listUserFollowers.observe(this.requireActivity(),{ listFollowers ->
            setFollowersData(listFollowers)
        })

        return binding.root
    }

    private fun setFollowersData(dataFollowers: List<UserGithubResponse>){
        val arrListUserFollowers = ArrayList<UserGithubResponse>()
        dataFollowers.forEach {
            arrListUserFollowers.addAll(listOf(it))
        }
        val adapter = ListGithubUsersAdapter(arrListUserFollowers, object: ListGithubUsersAdapter.ItemListener{

        })
        binding.rvFragFollowers.adapter = adapter
    }
}