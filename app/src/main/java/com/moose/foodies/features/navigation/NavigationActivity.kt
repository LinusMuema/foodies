package com.moose.foodies.features.navigation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moose.foodies.R
import com.moose.foodies.features.explore.Explore
import com.moose.foodies.features.favorites.Favorites
import com.moose.foodies.features.fridge.Fridge
import com.moose.foodies.features.home.Home
import com.moose.foodies.features.navigation.Screen.*
import com.moose.foodies.features.profile.Profile
import com.moose.foodies.theme.FoodiesTheme

class NavigationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun Content(){
        val navController = rememberNavController()
        val screens = listOf(Home, Explore, Fridge, Favorites, Profile)
        
        FoodiesTheme {
            Surface(color = MaterialTheme.colors.primary) {
                Scaffold(
                    backgroundColor = MaterialTheme.colors.primary,
                    bottomBar = {
                    BottomNavigation(backgroundColor = MaterialTheme.colors.primary) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        screens.forEach { screen ->
                            BottomNavigationItem(
                                alwaysShowLabel = false,
                                selectedContentColor = MaterialTheme.colors.onSurface,
                                unselectedContentColor = MaterialTheme.colors.secondaryVariant,
                                icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                                label = { Text(stringResource(screen.resourceId)) },
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        restoreState = true
                                        launchSingleTop = true
                                    }
                                }
                            )
                        }
                    }
                }) {
                    NavHost(navController, startDestination = Home.route, Modifier.padding(it)) {
                        composable(Home.route) { Home(navController) }
                        composable(Fridge.route) { Fridge(navController) }
                        composable(Explore.route) { Explore(navController) }
                        composable(Profile.route) { Profile(navController) }
                        composable(Favorites.route) { Favorites(navController) }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object Home : Screen("/home", R.string.home)
    object Fridge : Screen("/fridge", R.string.fridge)
    object Explore : Screen("/explore", R.string.explore)
    object Profile : Screen("/profile", R.string.profile)
    object Favorites : Screen("/favorites", R.string.favorites)
}