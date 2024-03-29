package com.dionkn.githubuserapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dionkn.githubuserapp.Util.SettingPreferences
import com.dionkn.githubuserapp.Util.ViewModelFactory
import com.dionkn.githubuserapp.ViewModel.SettingViewModel
import com.dionkn.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding?= null
    private val binding get() = _binding!!
    private lateinit var settingViewModel: SettingViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setUpDarkTheme()
        Handler().postDelayed(
            {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }, 3000
        )

    }

    private fun setUpDarkTheme(){
        val pref = SettingPreferences.getInstance(dataStore = dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this, { isDarkMode ->
            if(isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        })
    }
}