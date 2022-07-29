package com.dionkn.githubuserapp.Model.Response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchedUserResponse (
    @field:SerializedName("total_count")
    var total_count: Int = 0,

    @field:SerializedName("incomplete_results")
    var incomplete_results: Boolean = false,

    @field:SerializedName("items")
    var items: List<UserGithubResponse> ?= null
) : Parcelable