package com.dionkn.githubuserapp.Networking

import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers("Authorization: Bearer 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")
    @GET("/users")
    fun getGithubUsers(): Call<List<UserGithubResponse>>

    @Headers("Authorization: Bearer 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")
    @GET("/users/{login}")
    fun getDetailUsers(
        @Path("login") login : String
    ): Call<UserGithubResponse>
}