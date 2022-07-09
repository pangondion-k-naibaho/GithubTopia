package com.dionkn.githubuserapp.Model.Class

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubUser (
    var fullName : String = "",
    var userName : String = "",
    var userBio : String = "",
    var numberFollower : Int = 0,
    var numberFollowing : Int = 0,
    var urlProfilePict: String = "",
    var company : String = "",
    var location : String = "",
    var listUserRepo: List<UserRepo> ?= null
    ) : Parcelable
