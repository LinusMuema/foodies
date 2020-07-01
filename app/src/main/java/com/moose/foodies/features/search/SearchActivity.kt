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
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchViewModel by viewModels<SearchViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val query = intent.getStringExtra("recipeName")!!
        search_query.text = "'$query'"

        searchViewModel.searchRecipe(query)
    }
}