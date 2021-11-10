package com.moose.foodies.features.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.moose.foodies.R

sealed class Screen(val route: String, @StringRes val name: Int, @DrawableRes val icon: Int?) {

    // main screens
    object Add: Screen("/add", R.string.add, null)
    object Main: Screen("/main", R.string.main, null)
    object Recipe: Screen("/recipe", R.string.recipe, null)

    // bottom navigation screens
    object Home : Screen("/home", R.string.home, R.drawable.ic_home)
    object Fridge : Screen("/fridge", R.string.fridge, R.drawable.ic_fridge)
    object Explore : Screen("/explore", R.string.explore, R.drawable.ic_explore)
    object Profile : Screen("/profile", R.string.profile, R.drawable.ic_profile)
}