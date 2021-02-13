package com.moose.foodies.features.feature_favorites.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.moose.foodies.databinding.ActivityFavoritesBinding
import com.moose.foodies.features.feature_favorites.adapters.FavoritesAdapter
import com.moose.foodies.features.feature_home.domain.Recipe
import com.moose.foodies.features.feature_recipe.presentation.RecipeActivity
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.PreferenceHelper
import com.moose.foodies.util.extensions.push
import com.moose.foodies.util.onError
import com.moose.foodies.util.onSuccess
import dagger.android.AndroidInjection
import javax.inject.Inject

class FavoritesActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var favorites: MutableList<Recipe>
    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel by viewModels<FavoritesViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)

        viewModel.getFavorites()

        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        binding.back.setOnClickListener { onBackPressed() }

        viewModel.favorites.observe(this, { result ->
            result.onSuccess { recipes ->
                favorites = recipes.toMutableList()
                val favoritesAdapter = FavoritesAdapter(
                    favorites,
                    removeFavorite = { removeFavorite(it) },
                    showRecipe = { id ->
                        push<RecipeActivity> { it.putExtra("recipeId", id) }
                    }
                )

                binding.recyclerView.adapter = favoritesAdapter
            }
            result.onError { }
        })

        setContentView(binding.root)
    }

    private fun removeFavorite(position: Int) {
        val recipe = favorites[position]
        favorites.remove(recipe)
        binding.recyclerView.adapter!!.notifyDataSetChanged()

        val snackbar = Snackbar.make(binding.root, "Recipe removed from Favorites", Snackbar.LENGTH_SHORT)

        snackbar.setAction("Undo") {
            favorites.add(position, recipe)
            binding.recyclerView.adapter!!.notifyDataSetChanged()
        }

        snackbar.addCallback(object : Snackbar.Callback(){
            override fun onDismissed(transientBottomBar: Snackbar, event: Int) {
                if (event == DISMISS_EVENT_TIMEOUT || event == DISMISS_EVENT_CONSECUTIVE){
                    viewModel.removeFavorite(recipe.id)
                    PreferenceHelper.setBackupStatus(applicationContext, true)
                }
            }
        })

        snackbar.show()
    }

    override fun onPause() {
        super.onPause()
        if (PreferenceHelper.getBackupStatus(this))
            viewModel.startBackup()
    }
}

