package com.dionkn.githubuserapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dionkn.githubuserapp.Util.SettingPreferences
import com.dionkn.githubuserapp.Util.ViewModelFactory
import com.dionkn.githubuserapp.ViewModel.SettingViewModel
import com.dionkn.githubuserapp.databinding.ActivitySettingBinding
import androidx.datastore.preferences.core.Preferences

class SettingActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    private val TAG = SettingActivity::class.java.simpleName

    private var _binding : ActivitySettingBinding?= null
    private val binding get() = _binding!!
    private lateinit var settingViewModel: SettingViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this,{ isDarkMode ->
            if(isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchSetting1.isChecked = true
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchSetting1.isChecked = false
            }
        })

        binding.switchSetting1.setOnCheckedChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        fun newIntent(context: Context): Intent = Intent(context, SettingActivity::class.java)
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        settingViewModel.saveThemeSetting(p1)
    }
}