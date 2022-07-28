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