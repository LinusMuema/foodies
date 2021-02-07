package com.moose.foodies.features.feature_favorites

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.moose.foodies.R
import com.moose.foodies.models.Recipe
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.PreferenceHelper
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

        rv.setListener( object : SwipeLeftRightCallback.Listener {
            override fun onSwipedRight(position: Int) { return }

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
                        if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_CONSECUTIVE){
                            favoritesViewModel.removeFavorite(recipe)
                            PreferenceHelper.setBackupStatus(this@FavoritesActivity, true)
                        }
                    }
                })
                snackbar.show()
            }

        })

        favorites_back.setOnClickListener { onBackPressed() }
        favoritesViewModel.getFavorites()
    }

    override fun onPause() {
        super.onPause()
    }
}

