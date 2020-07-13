package com.moose.foodies.features.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.R
import com.moose.foodies.util.ActivityHelper
import com.moose.foodies.util.hideBottomBar
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

    private val searchViewModel by viewModels<SearchViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val query = intent.getStringExtra("recipeName")!!
        val title = resources.getString(R.string.search_query, query)
        search_title.text = title

        view_pager.adapter = SearchViewpagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(view_pager)

        searchViewModel.searchRecipe(query)

        search_back.setOnClickListener { onBackPressed() }
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}