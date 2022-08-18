package com.dionkn.githubuserapp.Data.Local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse

@Dao
interface FavoritedUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(favoritedUser: UserGithubResponse)

    @Query("SELECT * FROM favorited_user")
    fun getFavoritedUser(): LiveData<List<UserGithubResponse>>

    @Query("DELETE FROM favorited_user WHERE favorited_user.id = :id")
    fun removeFromFavoritedUser(id: Long): Int

    @Query("SELECT COUNT(*) FROM favorited_user WHERE favorited_user.id = :id")
    fun isFavoriteUser(id: Long): Int

}