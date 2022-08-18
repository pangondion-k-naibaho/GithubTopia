package com.dionkn.githubuserapp

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dionkn.githubuserapp.Model.Adapter.ListGithubUsersAdapter
import com.dionkn.githubuserapp.Model.Item.PopupDialogListener
import com.dionkn.githubuserapp.Model.Item.showPopupDialog
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.Model.Item.InputSearchView
import com.dionkn.githubuserapp.Util.SettingPreferences
import com.dionkn.githubuserapp.Util.ViewModelFactory
import com.dionkn.githubuserapp.ViewModel.HomeViewModel
import com.dionkn.githubuserapp.ViewModel.SettingViewModel
import com.dionkn.githubuserapp.databinding.ActivityHomeBinding
import java.util.*

class HomeActivity : AppCompatActivity() {

    private val TAG = HomeActivity::class.java.simpleName
    private lateinit var binding : ActivityHomeBinding

    private val homeViewModel by viewModels<HomeViewModel>()
    private var arrListUserData= ArrayList<UserGithubResponse>()
    private var arrListSearchedUserData= ArrayList<UserGithubResponse>()

    private lateinit var settingViewModel: SettingViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProgressBarColor()

        binding.homeAllRvusers.setHasFixedSize(true)
        binding.homeAllRvusers.layoutManager = LinearLayoutManager(this)

        binding.homeSearchRvusers.setHasFixedSize(true)
        binding.homeSearchRvusers.layoutManager = LinearLayoutManager(this)


        homeViewModel.getUserGithub()
        homeViewModel.listUserGithub.observe(this,{ listUsers ->
            setUserData(listUsers)
        })

        homeViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        homeViewModel.isFail.observe(this, {
            setUpWarning(it)
        })

        searchBarSetUp()

    }

    companion object{
        const val EXTRA_GITHUB_USER = "EXTRA_GITHUB_USER"
        const val EXTRA_STATE = "EXTRA_STATE"
    }

    private fun setProgressBarColor(){
        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this, { isDarkMode ->
            if(isDarkMode){
                binding.pbMain.indeterminateDrawable.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.MULTIPLY)
            }else{
                binding.pbMain.indeterminateDrawable.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.MULTIPLY)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.home_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_favorite ->{
                startActivity(
                    FavoriteActivity.newIntent(this@HomeActivity)
                )
                true
            }
            R.id.menu_setting->{
                startActivity(
                    SettingActivity.newIntent(this@HomeActivity)
                )
                true
            }
            else -> true
        }
    }

    private fun setUpWarning(isFail: Boolean){
        if(isFail == true){
            this@HomeActivity.showPopupDialog(
                AppCompatResources.getDrawable(this@HomeActivity,R.drawable.octocat_warning)!!,
                "We're sorry, there seems a problem",
                "OK",
                object: PopupDialogListener{
                    override fun onClickListener() {
                        this@HomeActivity.closeOptionsMenu()
                    }
                }
            )
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.pbMain.visibility = if(isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(userData: List<UserGithubResponse>){
        userData.forEach {
            arrListUserData.addAll(listOf(it))
        }
        val adapter = ListGithubUsersAdapter(arrListUserData, object: ListGithubUsersAdapter.ItemListener{
            override fun onItemClicked(item: UserGithubResponse) {
                Log.d(TAG, "item : $item")
                showDetailUser(item)
            }

        })
        binding.homeAllRvusers.adapter = adapter
    }

    private fun showDetailUser(user: UserGithubResponse){
        startActivity(
            DetailActivity.newIntent(this, user, true).apply {
                putExtra(EXTRA_GITHUB_USER, user)
                putExtra(EXTRA_STATE, true)
            }
        )
    }

    private fun searchBarSetUp(){
        binding.isvHome.apply {
            setTextHelper(getString(R.string.search_helper))
            setListener(object: InputSearchView.InputSearchListener{
                override fun onClickSearch() {
                    binding.homeAllRvusers.visibility = View.GONE
                    binding.homeSearchRvusers.visibility = View.GONE
                    userSearch(getText())
                }

                override fun onClearSearch() {
                    clearText()
                    with(binding){
                        when(homeSearchRvusers.visibility == View.VISIBLE){
                            true -> {
                                arrListSearchedUserData.clear()
                                homeSearchRvusers.visibility = View.GONE
                                homeAllRvusers.visibility = View.VISIBLE
                            }
                        }
                    }

                }

            })
        }
    }

    private fun userSearch(text: String){
        binding.homeAllRvusers.visibility = View.GONE
        binding.homeSearchRvusers.visibility = View.VISIBLE
        homeViewModel.getSearchedUser(text)
        homeViewModel.listSearchedUser.observe(this, { listSearchedUser ->
            Log.d(TAG, "listSearchedUser : ${listSearchedUser.toString()}")
            setSearchedUserData(listSearchedUser)
        })
    }

    private fun setSearchedUserData(userData: List<UserGithubResponse>){
        arrListSearchedUserData.clear()
        userData.forEach {
            arrListSearchedUserData.addAll(listOf(it))
        }

        val adapter = ListGithubUsersAdapter(arrListSearchedUserData, object: ListGithubUsersAdapter.ItemListener{
            override fun onItemClicked(item: UserGithubResponse) {
                Log.d(TAG, "item : $item")
                showDetailUser(item)
            }
        })
        binding.homeSearchRvusers.adapter = adapter
    }

}