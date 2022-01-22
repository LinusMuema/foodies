package com.moose.foodies.presentation.features.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.moose.foodies.R
import com.moose.foodies.presentation.features.add.Add
import com.moose.foodies.presentation.features.chef.Chef
import com.moose.foodies.presentation.features.recipe.Recipe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent { Content() }
    }

    @Composable
    private fun Content(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "/main") {
            composable("/add") { Add(navController) }
            composable("/main") { Main(navController) }
            composable(
                route = "/chef/{id}",
                deepLinks = listOf(navDeepLink { uriPattern = "http://foodies.moose.ac/chefs?id={id}" })
            ) { Chef(it.arguments?.getString("id"), navController)}

            composable(
                route = "/recipe/{id}",
                deepLinks = listOf(navDeepLink { uriPattern = "http://foodies.moose.ac/recipes?id={id}" })
            ) { Recipe(it.arguments?.getString("id"), navController)}
        }
    }
}

sealed class Screen(val route: String, @StringRes val name: Int, @DrawableRes val icon: Int?) {

    // main screens
    object Add: Screen("/add", R.string.add, null)
    object Main: Screen("/main", R.string.main, null)
    object Chef: Screen("/chef", R.string.chef, null)
    object Recipe: Screen("/recipe", R.string.recipe, null)

    // bottom navigation screens
    object Home : Screen("/home", R.string.home, R.drawable.ic_home)
    object Fridge : Screen("/fridge", R.string.fridge, R.drawable.ic_fridge)
    object Explore : Screen("/explore", R.string.explore, R.drawable.ic_explore)
    object Profile : Screen("/profile", R.string.profile, R.drawable.ic_profile)
}