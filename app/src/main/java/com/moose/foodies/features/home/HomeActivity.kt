package com.moose.foodies.features.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.mancj.materialsearchbar.MaterialSearchBar
import com.moose.foodies.R
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.features.favorites.FavoritesActivity
import com.moose.foodies.features.recipe.RecipeActivity
import com.moose.foodies.features.search.SearchActivity
import com.moose.foodies.util.*
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.carousel_item.view.*
import java.util.*
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
        this.setUpBoomMenu()

        val scale: Float = this.resources.displayMetrics.density
        val pixels = (HeightCalculator.getImageHeight(this) * scale + 0.5f).toInt()

        homeViewModel.state.observe(this, Observer {
            home_swipe.stopRefreshing()
            showSnackbar(home_layout, it.reason!!)
        })

        homeViewModel.recipes.observe(this, Observer {
            home_swipe.stopRefreshing()
            joke.text = it.joke
            trivia.text = it.trivia
            carousel.apply {
                size = it.recipes.size
                setCarouselViewListener { view, position ->
                    val recipe = it.recipes[position]
                    val url = recipe.info.image.replace("312x231", "636x393")
                    view.carousel_item.setHeight(pixels)
                    view.pick_image.loadCarouselImage(url)
                    view.pick_name.text = recipe.info.title
                    view.setOnClickListener {
                        startActivity(Intent(this@HomeActivity, RecipeActivity::class.java).putExtra("recipe", Gson().toJson(recipe)))
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

        //Search bar section
        recentSearches = PreferenceHelper.getRecentSearches(this)!!.split(",").toHashSet()
        searchBar.lastSuggestions = recentSearches.toMutableList()
        searchBar.setOnSearchActionListener(object : MaterialSearchBar.OnSearchActionListener{

            override fun onButtonClicked(buttonCode: Int) {
                Log.d("Search", "onButtonClicked: $buttonCode")
            }

            override fun onSearchStateChanged(enabled: Boolean) {
                Log.d("Search", "onSearchStateChanged: $enabled")
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                recentSearches.add(text.toString())
                startActivity(
                    Intent(this@HomeActivity, SearchActivity::class.java)
                        .putExtra("recipeName", text.toString()))
            }

        })


        //Copying the joke
        joke_copy.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("joke", joke.text)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(this, "Joke copied to clipboard", Toast.LENGTH_LONG).show()
        }

        //Copying the trivia
        trivia_copy.setOnClickListener {
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("trivia", trivia.text)
            clipboardManager.setPrimaryClip(clipData)

            Toast.makeText(this, "Trivia copied to clipboard", Toast.LENGTH_LONG).show()
        }

    }

    private fun setUpBoomMenu() {
        val icons = arrayOf(R.drawable.ic_home, R.drawable.ic_favorites, R.drawable.ic_account, R.drawable.ic_logout)
        for (icon in icons){
            val builder = SimpleCircleButton.Builder()
                .normalColorRes(R.color.primary)
                .normalImageRes(icon)
                .imagePadding(Rect(10, 10, 10, 10))
                .listener {
                    handleBoomClick(it)
                }

            bmb.addBuilder(builder)
        }
        bmb.buttonPlaceEnum = ButtonPlaceEnum.SC_4_2
        bmb.piecePlaceEnum = PiecePlaceEnum.DOT_4_2
    }

    private fun handleBoomClick(index: Int) {
        val activity = this.localClassName
        when(index){
            0 -> {
                if (!activity.contains("HomeActivity"))
                    startActivity(Intent(this, HomeActivity::class.java))
            }
            1 -> {
                if (!activity.contains("FavoritesActivity"))
                    startActivity(Intent(this, FavoritesActivity::class.java))
            }
            2 -> {
                Log.d("Boom", "handleBoomClick: account clicked")
            }
            3 -> {
                PreferenceHelper.setLogged(this, false)
                startActivity(Intent(this, AuthActivity::class.java))
            }
        }
    }

    private fun connectionAvailable(): Boolean {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null
    }

    override fun onPause() {
        super.onPause()
        PreferenceHelper.setRecentSearches(this, recentSearches.joinToString(separator = ","))
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceHelper.setRecentSearches(this, recentSearches.joinToString(separator = ","))
    }
}