package com.moose.foodies.features.feature_home

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.mancj.materialsearchbar.MaterialSearchBar
import com.moose.foodies.databinding.ActivityHomeBinding
import com.moose.foodies.features.feature_favorites.FavoritesActivity
import com.moose.foodies.features.feature_ingredients.IngredientsActivity
import com.moose.foodies.features.feature_search.SearchActivity
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.PreferenceHelper
import com.moose.foodies.util.hideBottomBar
import com.moose.foodies.util.push
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }
    private lateinit var binding: ActivityHomeBinding
    private lateinit var set:HashSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        binding.favoritesBtn.setOnClickListener { push<FavoritesActivity>() }
        binding.ingredientsBtn.setOnClickListener {push<IngredientsActivity>()}

        //Search bar config
        val recent = PreferenceHelper.getRecentSearches(this)!!
        set = recent.split(",").toHashSet()
        setSearchBar(set)

        setContentView(binding.root)
    }

    private fun setSearchBar(set: HashSet<String>) {
        val searches = set.toMutableList().filter { it != "" }

        with(binding){
            searchBar.isSuggestionsEnabled = searches.isNotEmpty()
            if (searches.isNotEmpty()) searchBar.lastSuggestions = searches

            searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{

                override fun onButtonClicked(buttonCode: Int) {
                    hideBottomBar()
                }

                override fun onSearchStateChanged(enabled: Boolean) {
                    hideBottomBar()
                }

                override fun onSearchConfirmed(text: CharSequence?) {
                    set.add(text.toString())
                    push<SearchActivity> {
                        it.putExtra("recipeName", text.toString())
                    }
                }

            })
        }
    }


    private fun connectionAvailable(): Boolean {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null
    }


    override fun onResume() {
        super.onResume()
        ActivityHelper.initialize(this)
    }

    override fun onPause() {
        super.onPause()
        PreferenceHelper.setRecentSearches(this, searchBar.lastSuggestions.joinToString(separator = ","))
    }
}