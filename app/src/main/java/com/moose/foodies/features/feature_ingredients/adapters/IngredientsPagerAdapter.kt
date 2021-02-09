package com.moose.foodies.features.feature_ingredients.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moose.foodies.features.feature_ingredients.presentation.IngredientsFragment

class IngredientsPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return IngredientsFragment()
    }
}