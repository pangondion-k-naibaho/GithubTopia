package com.dionkn.githubuserapp.Model.Class

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepo (
    var repoName : String = "",
    var toolUsed : String = ""
        ) : Parcelable