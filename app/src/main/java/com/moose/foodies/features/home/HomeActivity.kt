package com.moose.foodies.features.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.mancj.materialsearchbar.MaterialSearchBar
import com.moose.foodies.R
import com.moose.foodies.features.fridge.FridgeActivity
import com.moose.foodies.features.favorites.FavoritesActivity
import com.moose.foodies.features.recipe.RecipeActivity
import com.moose.foodies.features.search.SearchActivity
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.carousel_item.view.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private var ingredients: ArrayList<String> = ArrayList()
    private lateinit var recentSearches: HashSet<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val scale: Float = this.resources.displayMetrics.density
        val pixels = (HeightCalculator.getImageHeight(this) * scale + 0.5f).toInt()

        recipes_loading.setHeight(pixels)
        jokes_loading.setHeight(pixels)
        trivia_loading.setHeight(pixels)

        homeViewModel.state.observe(this, Observer {
            home_swipe.stopRefreshing()
            showSnackbar(home_layout, it.reason!!)
        })

        homeViewModel.recipes.observe(this, Observer { it ->

            home_swipe.stopRefreshing()
            jokes_loading.hide()
            joke.text = it.joke
            trivia_loading.hide()
            trivia.text = it.trivia
            recipes_loading.hide()
            carousel.apply {
                size = it.recipes.size
                setCarouselViewListener { view, position ->
                    val recipe = it.recipes[position]
                    val url = recipe.info.image.replace("312x231", "636x393")
                    view.carousel_item.setHeight(pixels)
                    view.pick_image.loadCarouselImage(url)
                    view.pick_name.text = recipe.info.title
                    view.setOnClickListener {
                        push<RecipeActivity>{
                            it.putExtra("recipe", Gson().toJson(recipe))
                        }
                    }
                }
                show()
            }
        })

        homeViewModel.getRecipes()
        home_swipe.setOnRefreshListener {
            if (connectionAvailable()) {
                homeViewModel.updateRecipes()
            }
            else {
                showSnackbar(home_layout, "Check your connection and try again")
                home_swipe.stopRefreshing()
            }
        }

        joke.setOnLongClickListener {
            addToClipboard(joke.text.toString(), "Joke")
            true
        }

        trivia.setOnLongClickListener{
            addToClipboard(trivia.text.toString(), "Trivia")
            true
        }

        favorites_btn.setOnClickListener { push<FavoritesActivity>() }
        fridge_btn.setOnClickListener {push<FridgeActivity>()}

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