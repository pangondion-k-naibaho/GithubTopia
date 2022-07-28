package com.dionkn.githubuserapp.Model.Response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserGithubResponse(
    @field:SerializedName("login")
    var login : String = "",

    @field:SerializedName("id")
    var id : Long = 0,

    @field:SerializedName("node_id")
    var node_id : String = "",

    @field:SerializedName("avatar_url")
    var avatar_url : String = "",

    @field:SerializedName("gravatar_id")
    var gravatar_id : String = "",

    @field:SerializedName("url")
    var url : String = "",

    @field:SerializedName("html_url")
    var html_url : String = "",

    @field:SerializedName("followers_url")
    var followers_url : String = "",

    @field:SerializedName("following_url")
    var following_url : String = "",

    @field:SerializedName("gists_url")
    var gists_url : String = "",

    @field:SerializedName("starred_url")
    var starred_url : String = "",

    @field:SerializedName("subscription_url")
    var subscription_url : String = "",

    @field:SerializedName("organization_url")
    var organization_url : String = "",

    @field:SerializedName("repos_url")
    var repos_url : String = "",

    @field:SerializedName("events_url")
    var events_url : String = "",

    @field:SerializedName("received_events_url")
    var received_events_url : String = "",

    @field:SerializedName("type")
    var type : String = "",

    @field:SerializedName("site_admin")
    var site_admin : Boolean = false,

    @field:SerializedName("name")
    var name : String = "",

    @field:SerializedName("company")
    var company : String ?= null,

    @field:SerializedName("blog")
    var blog : String ?= null,

    @field:SerializedName("location")
    var location : String ?= null,

    @field:SerializedName("email")
    var email: String ?= null,

    @field:SerializedName("hireable")
    var hireable : String ?= null,

    @field:SerializedName("bio")
    var bio : String ?= null,

    @field:SerializedName("twitter_username")
    var twitter_username: String ?= null,

    @field:SerializedName("public_repos")
    var public_repos : Int = 0,

    @field:SerializedName("public_gists")
    var public_gists : Int = 0,

    @field:SerializedName("followers")
    var followers: Int = 0,

    @field:SerializedName("following")
    var following: Int = 0,

    @field:SerializedName("created_at")
    var created_at : String = "",

    @field:SerializedName("updated_at")
    var updated_at : String = ""
) : Parcelable