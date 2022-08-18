package com.dionkn.githubuserapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.Data.Remote.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowersFollowingViewModel: ViewModel() {
    private val TAG = FollowersFollowingViewModel::class.java.simpleName

    private var _listUserFollowers = MutableLiveData<List<UserGithubResponse>>()
    val listUserFollowers : LiveData<List<UserGithubResponse>> = _listUserFollowers

    private var _listUserFollowing = MutableLiveData<List<UserGithubResponse>>()
    val listUserFollowing : LiveData<List<UserGithubResponse>> = _listUserFollowing

    fun getListFollowers(username: String){
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : retrofit2.Callback<List<UserGithubResponse>> {
            override fun onResponse(
                call: Call<List<UserGithubResponse>>,
                response: Response<List<UserGithubResponse>>
            ) {
                if(response.isSuccessful){
                    _listUserFollowers.value = response.body()
                    Log.d(TAG, "Success")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserGithubResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getListFollowing(username: String){
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : retrofit2.Callback<List<UserGithubResponse>> {
            override fun onResponse(
                call: Call<List<UserGithubResponse>>,
                response: Response<List<UserGithubResponse>>
            ) {
                if(response.isSuccessful){
                    _listUserFollowing.value = response.body()
                    Log.d(TAG, "Success")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserGithubResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}