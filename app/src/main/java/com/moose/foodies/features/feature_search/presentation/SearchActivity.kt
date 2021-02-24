package com.moose.foodies.features.feature_search.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.moose.foodies.R
import com.moose.foodies.databinding.ActivitySearchBinding
import com.moose.foodies.features.feature_search.adapters.SearchViewpagerAdapter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SearchActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val titles = arrayOf("Recipes", "Videos")
    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        val query = intent.getStringExtra("query")!!
        val title = resources.getString(R.string.search_query, query)

        binding = ActivitySearchBinding.inflate(layoutInflater)

        with(binding){
            searchQuery.text = title
            viewPager.adapter = SearchViewpagerAdapter(this@SearchActivity)
            back.setOnClickListener { onBackPressed() }

            TabLayoutMediator(tabs, viewPager){tab, position ->
                tab.text = titles[position]
            }.attach()

            setContentView(root)
        }

        viewModel.searchRecipe(query)


    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}