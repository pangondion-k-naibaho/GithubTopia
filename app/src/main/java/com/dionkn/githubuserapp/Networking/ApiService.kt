package com.dionkn.githubuserapp.Networking

import com.dionkn.githubuserapp.Model.Response.SearchedUserResponse
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Authorization: Bearer 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")
    @GET("/users")
    fun getGithubUsers(): Call<List<UserGithubResponse>>

    @Headers("Authorization: Bearer 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")
    @GET("/users/{login}")
    fun getDetailUsers(
        @Path("login") login : String
    ): Call<UserGithubResponse>

    @Headers("Authorization: Bearer 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")
    @GET("/users/{username}/followers")
    fun getUserFollowers(
        @Path("username") login : String
    ): Call<List<UserGithubResponse>>

    @Headers("Authorization: Bearer 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")
    @GET("/users/{username}/following")
    fun getUserFollowing(
        @Path("username") login : String
    ): Call<List<UserGithubResponse>>

    @Headers("Authorization: Bearer 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")
    @GET("/search/users")
    fun getSearchedUser(
        @Query("q", encoded = true) login: String
    ): Call<SearchedUserResponse>

}