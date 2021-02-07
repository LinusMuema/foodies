package com.moose.foodies.features.ingredients

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moose.foodies.models.RecipeSuggestion

class IngredientsPagerAdapter(fragmentActivity: FragmentActivity, private val recipe: RecipeSuggestion) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MissedIngredientFragment(recipe.missedIngredients)
            else -> UsedIngredientFragment(recipe.usedIngredients)
        }
    }

}