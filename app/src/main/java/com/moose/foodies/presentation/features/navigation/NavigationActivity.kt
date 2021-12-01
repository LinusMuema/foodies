package com.moose.foodies.presentation.features.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.moose.foodies.presentation.features.add.Add
import com.moose.foodies.presentation.features.recipe.Recipe
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
@ExperimentalCoilApi
@ExperimentalPagerApi
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
            composable("/main") { Main(navController) }
            composable("/recipe/{id}") { Recipe(it.arguments?.getString("id"), navController) }
            composable("/add") { Add(navController) }
        }
    }
}