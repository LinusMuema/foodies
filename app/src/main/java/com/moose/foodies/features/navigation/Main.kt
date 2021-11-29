package com.moose.foodies.features.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moose.foodies.features.explore.Explore
import com.moose.foodies.features.fridge.Fridge
import com.moose.foodies.features.home.ui.Home
import com.moose.foodies.features.navigation.Screen.*
import com.moose.foodies.features.profile.Profile
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.util.getActivity

@ExperimentalMaterialApi
@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
fun Main(mainController: NavHostController) {
    val navController = rememberNavController()
    val screens = listOf(Home, Explore, Fridge, Profile)

    FoodiesTheme {
        Surface {
            ProvideWindowInsets {
                Scaffold(
                    modifier = Modifier.systemBarsPadding(),
                    bottomBar = {
                        BottomNavigation(backgroundColor = MaterialTheme.colors.background) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            screens.forEach { screen ->
                                BottomNavigationItem(
                                    alwaysShowLabel = false,
                                    label = { Text(stringResource(screen.name)) },
                                    selectedContentColor = MaterialTheme.colors.primary,
                                    unselectedContentColor = MaterialTheme.colors.primaryVariant,
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

                                            val destination =
                                                navController.graph.findStartDestination().id
                                            popUpTo(destination) { saveState = true }
                                        }
                                    }
                                )
                            }
                        }
                    }) {
                    NavHost(navController, startDestination = Home.route, Modifier.padding(it)) {
                        composable(Home.route) { Home() }
                        composable(Fridge.route) { Fridge() }
                        composable(Explore.route) { Explore() }
                        composable(Profile.route) { Profile(mainController) }
                    }
                }
            }
        }
    }
}