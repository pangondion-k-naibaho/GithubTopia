package com.dionkn.githubuserapp.Data.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse

@Database(
    entities = [UserGithubResponse::class],
    version = 1
)

abstract class UserDatabase: RoomDatabase() {
    companion object{
        private var INSTANCE: UserDatabase?= null

        fun getDatabase(context: Context): UserDatabase?{
            if(INSTANCE == null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "User_Database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favoritedUserDao(): FavoritedUserDao
}