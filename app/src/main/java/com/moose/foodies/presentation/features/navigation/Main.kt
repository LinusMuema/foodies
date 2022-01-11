package com.moose.foodies.presentation.features.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moose.foodies.presentation.features.explore.Explore
import com.moose.foodies.presentation.features.fridge.Fridge
import com.moose.foodies.presentation.features.home.Home
import com.moose.foodies.presentation.features.profile.Profile
import com.moose.foodies.presentation.theme.FoodiesTheme
import com.moose.foodies.presentation.features.navigation.Screen.Home
import com.moose.foodies.presentation.features.navigation.Screen.Fridge
import com.moose.foodies.presentation.features.navigation.Screen.Explore
import com.moose.foodies.presentation.features.navigation.Screen.Profile
import dev.chrisbanes.snapper.ExperimentalSnapperApi

@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun Main(mainController: NavHostController) {
    val navController = rememberNavController()
    val screens = listOf(Home, Explore, Fridge, Profile)

    FoodiesTheme {

        val isDark = colors.isLight
        val color = colors.background
        val systemUiController = rememberSystemUiController()
        SideEffect {
            systemUiController.setSystemBarsColor(color = color, darkIcons = isDark)
        }

        Surface {
            ProvideWindowInsets {
                Scaffold(
                    modifier = Modifier.systemBarsPadding(),
                    bottomBar = {
                        BottomNavigation(backgroundColor = colors.background) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            screens.forEach { screen ->
                                BottomNavigationItem(
                                    alwaysShowLabel = false,
                                    label = { Text(stringResource(screen.name)) },
                                    selectedContentColor = colors.primary,
                                    unselectedContentColor = colors.primaryVariant,
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    icon = {
                                        Icon(
                                            painter = painterResource(screen.icon!!),
                                            modifier = Modifier.size(22.dp),
                                            contentDescription = null
                                        )
                                    },
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            restoreState = true
                                            launchSingleTop = true

                                            val destination = navController.graph.findStartDestination().id
                                            popUpTo(destination) { saveState = true }
                                        }
                                    }
                                )
                            }
                        }
                    }) {
                    NavHost(navController, startDestination = Home.route, Modifier.padding(it)) {
                        composable(Home.route) { Home(mainController) }
                        composable(Fridge.route) { Fridge() }
                        composable(Explore.route) { Explore(mainController) }
                        composable(Profile.route) { Profile(mainController) }
                    }
                }
            }
        }
    }
}