package com.moose.foodies.presentation.features.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.moose.foodies.presentation.components.SmallSpacing
import com.moose.foodies.presentation.components.TinySpacing
import com.moose.foodies.util.customTabIndicatorOffset
import kotlinx.coroutines.launch

@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
fun Home(controller: NavController){
    val viewmodel: HomeViewmodel = hiltViewModel()
    val chefs by viewmodel.chefs.observeAsState()
    val recipes by viewmodel.recipes.observeAsState()
    val profile by viewmodel.profile.observeAsState()
    val refreshing by viewmodel.refreshing.observeAsState()

    val coroutineScope = rememberCoroutineScope()

    SwipeRefresh(
        state =  rememberSwipeRefreshState(refreshing!!),
        onRefresh = { viewmodel.fetchData() },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                scale = true,
                state = state,
                contentColor = colors.primary,
                refreshTriggerDistance = trigger,
                backgroundColor = colors.background,
            )
        }
    ) {
        Column(modifier = Modifier.animateContentSize().verticalScroll(rememberScrollState())) {
            SmallSpacing()
            profile?.let { Header(profile = it) }
            SmallSpacing()

            recipes?.let {
                val items = it.filter { recipe -> recipe.type == viewmodel.type }
                val highlightState = rememberPagerState(pageCount = items.size,  initialOffscreenLimit = 2)
                HorizontalPager(
                    state = highlightState,
                    horizontalAlignment = Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RecipeCard(controller, items[it])
                }
            }
            SmallSpacing()
            Row(
                verticalAlignment = CenterVertically,
                horizontalArrangement = SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Discover Chefs",
                    style = typography.h5.copy(color = colors.primary)
                )
                Text(
                    text = "See all",
                    style = typography.body1.copy(color = colors.primary)
                )
            }
            chefs?.let {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp),
                ) {
                    items(it){
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = rememberImagePainter(
                                    data = it.avatar,
                                    builder = { transformations(CircleCropTransformation()) }
                                ),
                                contentDescription = "${it.username} avatar",
                                modifier = Modifier
                                    .size(75.dp)
                                    .clip(shapes.large)
                                    .clickable { }
                            )
                            TinySpacing()
                            Text(it.username, style = typography.body1)
                        }
                    }
                }
            }
            SmallSpacing()
            Text(
                text = "Recipes",
                modifier = Modifier.padding(10.dp),
                style = typography.h5.copy(color = colors.primary)
            )
            recipes?.let {
                val recipesState = rememberPagerState(pageCount = 4)
                val titles = mutableListOf("Breakfast", "Snacks", "Main", "Others")
                titles.remove(viewmodel.type)

                val items = it.filter { recipe -> recipe.type == titles[recipesState.currentPage]}

                TabRow(
                    backgroundColor = Color.Transparent,
                    selectedTabIndex = recipesState.currentPage,
                    divider = {},
                    indicator = { positions ->
                        TabRowDefaults.Indicator(
                            height = 7.5.dp,
                            color = colors.secondary,
                            modifier = Modifier.customTabIndicatorOffset(positions[recipesState.currentPage])
                        )
                    }
                ) {
                    titles.forEachIndexed { index, title ->
                        val color =
                            if (recipesState.currentPage == index) colors.secondary else colors.onSurface
                        Tab(
                            selected = false,
                            onClick = { coroutineScope.launch { recipesState.animateScrollToPage(index) } }) {
                            Text(title, color = color, modifier = Modifier.padding(15.dp))
                        }
                    }
                }
                RecipeItems(controller, items)
            }
        }
    }
}