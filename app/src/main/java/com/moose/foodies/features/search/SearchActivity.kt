package com.moose.foodies.features.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.moose.foodies.R
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

        val titles = arrayOf("Recipes", "Videos")
        pager.adapter = SearchViewpagerAdapter(this)
        TabLayoutMediator(tab_layout, pager){tab, position ->
            tab.text = titles[position]

        }.attach()

        searchViewModel.searchRecipe(query)
    }
}