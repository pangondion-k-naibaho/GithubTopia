package com.dionkn.githubuserapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dionkn.githubuserapp.Model.Adapter.ListGithubUsersAdapter
import com.dionkn.githubuserapp.Model.Response.UserGithubResponse
import com.dionkn.githubuserapp.ViewModel.FavoriteViewModel
import com.dionkn.githubuserapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private val TAG = FavoriteActivity::class.java.simpleName
    private var _binding: ActivityFavoriteBinding?= null
    private val binding get() = _binding!!
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var arrListFavoritedUser = ArrayList<UserGithubResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorited User"

        setUpLoading(true)
        setUpRecyclerView()
        setupViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        arrListFavoritedUser.clear()
    }

    companion object{
        const val EXTRA_GITHUB_USER = "EXTRA_GITHUB_USER"
        const val EXTRA_STATE = "EXTRA_STATE"
        fun newIntent(context: Context): Intent = Intent(context, FavoriteActivity::class.java)
    }

    private fun setUpLoading(state: Boolean){
        binding.pbfavorite.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setUpRecyclerView(){
        with(binding){
            favoriteRvUser.setHasFixedSize(true)
            favoriteRvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
    }

    private fun setupViewModel(){
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        favoriteViewModel.getFavoriteUsers()!!.observe(this){ favoritedUsers ->

            favoritedUsers.forEach {
                arrListFavoritedUser.addAll(listOf(it))
            }

            if(favoritedUsers.count() != 0){
                setUpEmptyContent(false)
                val adapter = ListGithubUsersAdapter(arrListFavoritedUser, object: ListGithubUsersAdapter.ItemListener{
                    override fun onItemClicked(item: UserGithubResponse) {
                        Log.d(TAG, "user Selected: $item")
                        startActivity(
                            DetailActivity.newIntent(this@FavoriteActivity, item, false).apply {
                                putExtra(EXTRA_GITHUB_USER, item)
                                putExtra(EXTRA_STATE, true)

                            }
                        )
                    }
                })
                binding.favoriteRvUser.adapter = adapter
                setUpLoading(false)
            }else {
                setUpEmptyContent(true)
                setUpLoading(false)
            }

        }
    }

    private fun setUpEmptyContent(state: Boolean){
        if(state){
            binding.favoriteRvUser.visibility = View.GONE
            binding.tvEmptyList.apply {
                visibility = View.VISIBLE
                var uniCode = 0x1F525
                val emoji = String(Character.toChars(uniCode))
                val text = getString(R.string.empty_list_favorite_roast) + " $emoji $emoji"
                setText(text)
            }
        }else{
            binding.favoriteRvUser.visibility = View.VISIBLE
            binding.tvEmptyList.visibility = View.GONE
        }
    }
}