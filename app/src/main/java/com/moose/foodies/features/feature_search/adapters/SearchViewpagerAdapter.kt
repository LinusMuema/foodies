package com.moose.foodies.features.feature_search.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moose.foodies.features.feature_search.presentation.RecipesFragment
import com.moose.foodies.features.feature_search.presentation.VideosFragment

class SearchViewpagerAdapter(fragment: FragmentActivity): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> VideosFragment()
            else -> RecipesFragment()
        }
    }
}