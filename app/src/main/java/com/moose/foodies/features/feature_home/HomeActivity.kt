package com.moose.foodies.features.feature_home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mancj.materialsearchbar.MaterialSearchBar
import com.moose.foodies.R
import com.moose.foodies.features.feature_favorites.FavoritesActivity
import com.moose.foodies.features.feature_ingredients.IngredientsActivity
import com.moose.foodies.features.feature_search.SearchActivity
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.carousel_item.view.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private lateinit var recentSearches: HashSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        favorites_btn.setOnClickListener { push<FavoritesActivity>() }
        ingredients_btn.setOnClickListener {push<IngredientsActivity>()}

        //Search bar section
        recentSearches = PreferenceHelper.getRecentSearches(this)!!.split(",").toHashSet()
        searchBar.lastSuggestions = recentSearches.toMutableList()
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{

            override fun onButtonClicked(buttonCode: Int) {
                this@HomeActivity.hideBottomBar()
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                this@HomeActivity.hideBottomBar()
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                recentSearches.add(text.toString())
                push<SearchActivity> {
                    it.putExtra("recipeName", text.toString())
                }
            }

        })
    }


    private fun connectionAvailable(): Boolean {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null
    }

    private fun addToClipboard(text: String, type: String){
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(type, text)
        clipboardManager.setPrimaryClip(clipData)

        Toast.makeText(this, "$type copied to clipboard", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        ActivityHelper.initialize(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceHelper.setRecentSearches(this, searchBar.lastSuggestions.joinToString(separator = ","))
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceHelper.setRecentSearches(this, searchBar.lastSuggestions.joinToString(separator = ","))
    }
}