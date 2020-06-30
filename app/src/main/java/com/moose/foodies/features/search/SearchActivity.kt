package com.moose.foodies.features.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.R
import com.moose.foodies.util.PreferenceHelper
import dagger.android.AndroidInjection
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchViewModel by viewModels<SearchViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val searchType = intent.getIntExtra("searchType", 0)
        if (searchType == 0) {
            setContentView(R.layout.activity_search)
            wordSearchActivity()
        }
        else{
            setContentView(R.layout.activity_fridge)
            fridgeSearchActivity()
        }
    }

    private fun wordSearchActivity() {
        Log.d("Token", "wordSearchActivity: ${PreferenceHelper.getAccessToken(FoodiesApplication.getInstance())}")
        searchViewModel.searchRecipe(intent.getStringExtra("recipeName")!!)
    }

    private fun fridgeSearchActivity() {
        searchViewModel.searchRecipeByIngredients(intent.getStringExtra("ingredients")!!)
    }
}