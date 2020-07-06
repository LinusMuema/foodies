package com.moose.foodies.features.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private val titles = arrayOf("Recipes", "Videos")

class SearchViewpagerAdapter(fragment: FragmentManager): FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            1 -> VideosFragment()
            else -> RecipesFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount():  Int = 2
}