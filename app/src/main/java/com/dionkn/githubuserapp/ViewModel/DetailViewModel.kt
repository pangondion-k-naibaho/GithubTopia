package com.dionkn.githubuserapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.Networking.ApiConfig
import retrofit2.Call
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val TAG = DetailViewModel::class.java.simpleName
    private val _detailUser = MutableLiveData<UserGithubResponse>()
    val detailUser : LiveData<UserGithubResponse> = _detailUser

     fun getDetailUser(deliveredUsername: String){
        val client = ApiConfig.getApiService().getDetailUsers(deliveredUsername)
        client.enqueue(object: retrofit2.Callback<UserGithubResponse>{
            override fun onResponse(
                call: Call<UserGithubResponse>,
                response: Response<UserGithubResponse>
            ) {
                if(response.isSuccessful){
                    _detailUser.value = response.body()
                }else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}