package com.moose.foodies.features.navigation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.moose.foodies.R
import com.moose.foodies.features.explore.Explore
import com.moose.foodies.features.fridge.Fridge
import com.moose.foodies.features.home.ui.Home
import com.moose.foodies.features.navigation.Screen.*
import com.moose.foodies.features.profile.Profile
import com.moose.foodies.theme.FoodiesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalCoilApi
@ExperimentalPagerApi
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
        val screens = listOf(Home, Explore, Fridge, Profile)
        
        FoodiesTheme {
            Surface {
                Scaffold(
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

                                        val destination = navController.graph.findStartDestination().id
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
                        composable(Profile.route) { Profile() }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String, @StringRes val name: Int, @DrawableRes val icon: Int?) {
    object Home : Screen("/home", R.string.home, R.drawable.ic_home)
    object Fridge : Screen("/fridge", R.string.fridge, R.drawable.ic_fridge)
    object Explore : Screen("/explore", R.string.explore, R.drawable.ic_explore)
    object Profile : Screen("/profile", R.string.profile, R.drawable.ic_profile)
}