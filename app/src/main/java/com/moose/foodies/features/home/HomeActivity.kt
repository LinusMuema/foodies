package com.moose.foodies.features.home

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.android.material.chip.Chip
import com.google.gson.Gson
import com.moose.foodies.R
import com.moose.foodies.features.BaseActivity
import com.moose.foodies.features.recipe.RecipeActivity
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.carousel_item.view.*
import javax.inject.Inject

class HomeActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var heightCalculator: HeightCalculator

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }
    private var ingredients: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.setUpBoomMenu()

        val scale: Float = this.resources.displayMetrics.density
        val pixels = (heightCalculator.getImageHeight() * scale + 0.5f).toInt()

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

        btn_add.setOnClickListener {
            val chip = Chip(this@HomeActivity)
            chip.hide()
            chip.setOnCloseIconClickListener {
                chipGroup.removeView(chip)
            }
            MaterialDialog(this).show {
                input(hint = "Enter the ingredient"){_, text ->
                    if (text.isEmpty()) return@input
                    ingredients.add(text.toString())
                    chip.text = text.toString()
                    chip.isCloseIconVisible = true
                    chip.show()
                    this@HomeActivity.hideBottomBar()
                }
            }
            chipGroup.addView(chip)
        }

        search_view.setOnSearchListener(object : FloatingSearchView.OnSearchListener  {
            override fun onSearchAction(currentQuery: String?) {
                this@HomeActivity.hideBottomBar()
                Log.d("Search", "onSearchAction: $currentQuery")
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                this@HomeActivity.hideBottomBar()
                Log.d("Search", "onSuggestionClicked: $searchSuggestion")
            }

        })
    }

    private fun connectionAvailable(): Boolean {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null
    }
}