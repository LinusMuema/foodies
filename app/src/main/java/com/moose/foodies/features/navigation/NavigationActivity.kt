package com.moose.foodies.features.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.moose.foodies.features.add.Add
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalCoilApi
@ExperimentalPagerApi
class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }

    @Composable
    private fun Content(){
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "/main") {
            composable("/main") { Main(navController) }
            composable("/recipe") { Main(navController) }
            composable("/add") { Add(navController) }
        }
    }
}