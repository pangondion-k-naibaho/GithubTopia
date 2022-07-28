package com.dionkn.githubuserapp.Networking

import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("Authorization: Bearer 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")
    @GET("/users")
    fun getGithubUsers(): Call<List<UserGithubResponse>>
}