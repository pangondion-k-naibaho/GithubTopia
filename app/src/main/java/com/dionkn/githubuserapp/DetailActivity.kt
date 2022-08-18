package com.dionkn.githubuserapp

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dionkn.githubuserapp.Model.Adapter.DetailFragmentAdapter
import com.dionkn.githubuserapp.Model.Item.PopupDialogListener
import com.dionkn.githubuserapp.Model.Item.showPopupDialog
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.Util.SettingPreferences
import com.dionkn.githubuserapp.Util.ViewModelFactory
import com.dionkn.githubuserapp.ViewModel.DetailViewModel
import com.dionkn.githubuserapp.ViewModel.FavoriteViewModel
import com.dionkn.githubuserapp.ViewModel.SettingViewModel
import com.dionkn.githubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private val TAG = DetailActivity::class.java.simpleName
    private var _binding : ActivityDetailBinding?= null
    private val binding get() = _binding!!
    private lateinit var deliveredUser: UserGithubResponse
    private var deliveredState : Boolean = false
    private val detailViewModel by viewModels<DetailViewModel>()

    private var isFavorite: Boolean = false
    private lateinit var favoriteViewModel: FavoriteViewModel

    private lateinit var settingViewModel: SettingViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setProgressBarColor()

        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        detailViewModel.isLoading.observe(this, {
            setUpLoading(it)
        })

        detailViewModel.isFail.observe(this, {
            setUpWarning(it)
        })

        deliveredUser = intent.getParcelableExtra<UserGithubResponse>(EXTRA_GITHUB_USER) as UserGithubResponse
        deliveredUser?.login.let {
            getUser(deliveredUser)
        }

        deliveredState = intent.extras?.getBoolean(EXTRA_STATE)!!

//        setupActionBar()
//        initBundle()
        setStatusFavorite(deliveredUser)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setProgressBarColor(){
        val pref = SettingPreferences.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]
        settingViewModel.getThemeSettings().observe(this, { isDarkMode ->
            if(isDarkMode){
                //ProgressBar
                binding.pbDetail.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.MULTIPLY)

                //TabLayout
                binding.tlDetailUser.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.white))
                binding.tlDetailUser.setTabTextColors(ContextCompat.getColor(this, R.color.grey),ContextCompat.getColor(this, R.color.white))
            }else{
                //ProgressBar
                binding.pbDetail.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
                    ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.MULTIPLY)

                //TabLayout
                binding.tlDetailUser.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.black))
                binding.tlDetailUser.setTabTextColors(ContextCompat.getColor(this, R.color.grey),ContextCompat.getColor(this, R.color.black))
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.detail_menu, menu)

        val item: MenuItem = menu!!.findItem(R.id.menu_favorite_user)
        if(isFavorite){
            item.setIcon(R.drawable.ic_favorite_full)
        }else{
            item.setIcon(R.drawable.ic_favorite_not_full)
        }
        return deliveredState
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        return when(item.itemId){
            R.id.menu_favorite_user->{
                if(isFavorite){
                    item.setIcon(R.drawable.ic_favorite_not_full)
                    isFavorite = false
                    deliveredUser.id?.let { favoriteViewModel.removeFavoritedUser(it) }
                    displaySnackbar("User removed from favorite user...")
                }else{
                    item.setIcon(R.drawable.ic_favorite_full)
                    isFavorite = true
                    favoriteViewModel.addToFavorite(deliveredUser)
                    displaySnackbar("User added to favorite user...")
                }
                true
            }
            else -> true
        }
    }

    companion object{
        const val EXTRA_GITHUB_USER = "EXTRA_GITHUB_USER"
        const val EXTRA_STATE = "EXTRA_STATE"
        fun newIntent(context: Context, userDelivered: UserGithubResponse, stateDelivered: Boolean) : Intent = Intent(context, DetailActivity::class.java)
            .putExtra(EXTRA_GITHUB_USER, userDelivered)
            .putExtra(EXTRA_STATE, stateDelivered)
    }


    private fun setupActionBar(){
        var actionBar = getSupportActionBar()
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setTitle("Detail User")
        }
    }


    private fun initBundle(){
        deliveredUser = intent.getParcelableExtra<UserGithubResponse>(EXTRA_GITHUB_USER) as UserGithubResponse
        Log.d(TAG, "deliveredUsername: ${deliveredUser}")
        getUser(deliveredUser)
    }

    private fun setUpLoading(isLoading: Boolean){
        binding.pbDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUpWarning(isFail: Boolean){
        if(isFail == true){
            this@DetailActivity.showPopupDialog(
                AppCompatResources.getDrawable(this@DetailActivity, R.drawable.octocat_warning)!!,
                "We're sorry, there seems a problem",
                "OK",
                object: PopupDialogListener{
                    override fun onClickListener() {
                        this@DetailActivity.closeOptionsMenu()
                    }
                }
            )
        }
    }

    private fun getUser(user: UserGithubResponse){
        detailViewModel.getDetailUser(user.login)
        detailViewModel.detailUser.observe(this, { user ->
           setUpDisplay(user)
        })
    }

    private fun setUpDisplay(userGithub: UserGithubResponse){
        with(binding){
            Glide.with(this@DetailActivity).load(userGithub.avatar_url)
                .into(ivDetailuserPic)

            tvDetailuserFullname.text = userGithub.login
            tvDetailuserNickname.text = when(userGithub.name){
                null, "" -> " - "
                else -> userGithub.name
            }
            tvDetailuserCompany.text = when(userGithub.company){
                null, "" -> " - "
                else -> " ${userGithub.company} "
            }
            tvDetailuserLocation.text = when(userGithub.location){
                null, "" -> " - "
                else -> " ${userGithub.location} "
            }
        }
        val arrayTitle = arrayOf(
            "Followers (${userGithub.followers})",
            "Following (${userGithub.following})"
        )
        setupFragment(userGithub, arrayTitle)
    }

    private fun setupFragment(userGithub: UserGithubResponse, arrayTitle: Array<String>){
        with(binding){
            val detailPagerAdapter = DetailFragmentAdapter(this@DetailActivity, userGithub)
            vpDetailUser.adapter = detailPagerAdapter
            TabLayoutMediator(tlDetailUser, vpDetailUser, false, true){ tab, position ->
                tab.text = arrayTitle[position]
            }.attach()
        }
    }

    private fun displaySnackbar(text: String){
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun setStatusFavorite(userGithub: UserGithubResponse){
        CoroutineScope(Dispatchers.IO).launch {
            val count = userGithub.id.let { favoriteViewModel.isFavoritedUser(it) }
            isFavorite = count!! > 0
        }
    }

}