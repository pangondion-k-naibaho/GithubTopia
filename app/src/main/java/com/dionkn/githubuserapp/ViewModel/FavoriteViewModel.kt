package com.dionkn.githubuserapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dionkn.githubuserapp.Data.Local.FavoritedUserDao
import com.dionkn.githubuserapp.Data.Local.UserDatabase
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application){
    private var userFavoritedDao: FavoritedUserDao?= null
    private var userFavoriteDB: UserDatabase?= UserDatabase.getDatabase(application)

    init {
        userFavoritedDao = userFavoriteDB?.favoritedUserDao()
    }

    fun getFavoriteUsers(): LiveData<List<UserGithubResponse>>?{
        return userFavoritedDao?.getFavoritedUser()
    }

    fun addToFavorite(data: UserGithubResponse){
        CoroutineScope(Dispatchers.IO).launch {
            val favoritedUser = UserGithubResponse(
                data.login,
                data.id,
                data.node_id,
                data.avatar_url,
                data.gravatar_id,
                data.url,
                data.html_url,
                data.followers_url,
                data.following_url,
                data.gists_url,
                data.starred_url,
                data.subscription_url,
                data.organization_url,
                data.repos_url,
                data.events_url,
                data.received_events_url,
                data.type,
                data.site_admin,
                data.name,
                data.company,
                data.blog,
                data.location,
                data.email,
                data.hireable,
                data.bio,
                data.twitter_username,
                data.public_repos,
                data.public_gists,
                data.followers,
                data.following,
                data.created_at,
                data.updated_at
            )
            userFavoritedDao?.addToFavorite(favoritedUser)
        }
    }

    fun removeFavoritedUser(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            userFavoritedDao?.removeFromFavoritedUser(id)
        }
    }

    fun isFavoritedUser(id: Long) = userFavoritedDao?.isFavoriteUser(id)
}