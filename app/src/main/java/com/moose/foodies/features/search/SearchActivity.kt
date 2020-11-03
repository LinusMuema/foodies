package com.moose.foodies.features.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.moose.foodies.R
import com.moose.foodies.util.ActivityHelper
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val titles = arrayOf("Recipes", "Videos")

    private val searchViewModel by viewModels<SearchViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val query = intent.getStringExtra("recipeName")!!
        val title = resources.getString(R.string.search_query, query)
        search_title.text = title

        search_layout.setOnRefreshListener {
            searchViewModel.searchRecipe(query)
        }

        searchViewModel.loadState.observe(this, {
            search_layout.isRefreshing = it
        })

        view_pager.adapter = SearchViewpagerAdapter(this)
        TabLayoutMediator(tabs, view_pager){tab, position ->
            tab.text = titles[position]
        }.attach()

        searchViewModel.searchRecipe(query)

        search_back.setOnClickListener { onBackPressed() }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}