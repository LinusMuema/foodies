package com.moose.foodies.presentation.features.home.feed

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.moose.foodies.R

sealed class Screen(val route: String, @StringRes val name: Int, @DrawableRes val icon: Int?) {
    // bottom navigation screens
    object Feed : Screen("/feed", R.string.home, R.drawable.ic_home)
    object Fridge : Screen("/fridge", R.string.fridge, R.drawable.ic_fridge)
    object Explore : Screen("/explore", R.string.explore, R.drawable.ic_explore)
    object Profile : Screen("/profile", R.string.profile, R.drawable.ic_profile)
}