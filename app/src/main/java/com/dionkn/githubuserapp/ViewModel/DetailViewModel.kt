package com.dionkn.githubuserapp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.Data.Remote.ApiConfig
import retrofit2.Call
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val TAG = DetailViewModel::class.java.simpleName
    private val _detailUser = MutableLiveData<UserGithubResponse>()
    val detailUser : LiveData<UserGithubResponse> = _detailUser

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _isFail = MutableLiveData<Boolean>()
    val isFail: LiveData<Boolean> = _isFail

     fun getDetailUser(deliveredUsername: String){
         _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUsers(deliveredUsername)
        client.enqueue(object: retrofit2.Callback<UserGithubResponse>{
            override fun onResponse(
                call: Call<UserGithubResponse>,
                response: Response<UserGithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _detailUser.value = response.body()
                }else {
                    _isFail.value = true
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGithubResponse>, t: Throwable) {
                _isLoading.value = false
                _isFail.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}