package com.moose.foodies.features.favorites

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.moose.foodies.R
import com.moose.foodies.features.recipe.RecipeActivity
import com.moose.foodies.models.Recipe
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.HeightCalculator
import com.moose.foodies.util.push
import com.moose.foodies.util.showSnackbar
import com.tsuryo.swipeablerv.SwipeLeftRightCallback
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_favorites.*
import javax.inject.Inject

class FavoritesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var favorites: ArrayList<Recipe>

    private val favoritesViewModel by viewModels<FavoritesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        val scale: Float = this.resources.displayMetrics.density
        val pixels = (HeightCalculator.getImageHeight(this) * scale + 0.5f).toInt()

        favoritesViewModel.response.observe(this, Observer { it ->
            favorites = it as ArrayList<Recipe>
            rv.layoutManager = LinearLayoutManager(this)
            rv.adapter = FavoritesAdapter(favorites, pixels) {recipe ->
                push<RecipeActivity>{
                    it.putExtra("recipe", Gson().toJson(recipe))
                }
            }
        })

        favoritesViewModel.exception.observe(this, Observer {
            showSnackbar(favorites_layout, it)
        })

        rv.setListener( object : SwipeLeftRightCallback.Listener {
            override fun onSwipedRight(position: Int) {
                Log.d("Swipe", "onSwipedRight: swipe right done")
            }

            override fun onSwipedLeft(position: Int) {
                val recipe = favorites[position]
                favorites.remove(recipe)
                rv.adapter!!.notifyDataSetChanged()
                val snackbar = Snackbar.make(favorites_layout, "Recipe removed from Favorites", Snackbar.LENGTH_SHORT)
                snackbar.setAction("Undo") {
                    favorites.add(position, recipe)
                    rv.adapter!!.notifyDataSetChanged()
                }
                snackbar.addCallback(object : Snackbar.Callback(){
                    override fun onDismissed(transientBottomBar: Snackbar, event: Int) {
                        if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_CONSECUTIVE)
                            favoritesViewModel.removeFavorite(recipe)
                    }
                })
                snackbar.show()
            }

        })

        favorites_back.setOnClickListener { onBackPressed() }
        favoritesViewModel.getFavorites()
    }
}

