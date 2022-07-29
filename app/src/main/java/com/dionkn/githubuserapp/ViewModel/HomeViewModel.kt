package com.dionkn.githubuserapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dionkn.githubuserapp.Model.Response.SearchedUserResponse
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.Networking.ApiConfig
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel() {
    private val TAG = HomeViewModel::class.java.simpleName

    private var _listUserGithub = MutableLiveData<List<UserGithubResponse>>()
    val listUserGithub : LiveData<List<UserGithubResponse>> = _listUserGithub

    private var _listSearchedUser = MutableLiveData<List<UserGithubResponse>>()
    val listSearchedUser : LiveData<List<UserGithubResponse>> = _listSearchedUser

    companion object{
        private const val TOKEN = "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"
    }

    fun getUserGithub(){
        val client = ApiConfig.getApiService().getGithubUsers()
        client.enqueue(object: retrofit2.Callback<List<UserGithubResponse>>{
            override fun onResponse(
                call: Call<List<UserGithubResponse>>,
                response: Response<List<UserGithubResponse>>
            ) {
                if(response.isSuccessful){
                    _listUserGithub.value = response.body()
                    Log.d(TAG, "Success")
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserGithubResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }


        })
    }

    fun getSearchedUser(deliveredString: String){
        val client = ApiConfig.getApiService().getSearchedUser(deliveredString)
        client.enqueue(object: retrofit2.Callback<SearchedUserResponse>{
            override fun onResponse(
                call: Call<SearchedUserResponse>,
                response: Response<SearchedUserResponse>
            ) {
                if (response.isSuccessful){
                    _listSearchedUser.value = response.body()?.items
                    Log.d(TAG, "Success")
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchedUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}