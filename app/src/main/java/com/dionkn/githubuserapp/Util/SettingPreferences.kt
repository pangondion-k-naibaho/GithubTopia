package com.dionkn.githubuserapp.Util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val theme = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean>{
        return dataStore.data.map { preferences ->
            preferences[theme] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        dataStore.edit { preferences ->
            preferences[theme] = isDarkModeActive
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: SettingPreferences?= null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}