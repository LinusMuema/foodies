package com.moose.foodies.presentation.features.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyRow
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
import com.moose.foodies.util.customTabIndicatorOffset
import kotlinx.coroutines.launch

@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
fun Home(){
    val viewmodel: HomeViewmodel = hiltViewModel()
    val profile = viewmodel.profile.observeAsState().value
    val refreshing by viewmodel.refreshing.observeAsState()
    val highlightState = rememberPagerState(pageCount = 10,  initialOffscreenLimit = 2)

    val coroutineScope = rememberCoroutineScope()
    val recipesState = rememberPagerState(pageCount = 5)
    val titles = listOf("New", "Breakfast", "Lunch", "Supper", "Snacks")

    SwipeRefresh(
        state =  rememberSwipeRefreshState(refreshing!!),
        onRefresh = { viewmodel.fetchData() },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                scale = true,
                backgroundColor = colors.background,
                contentColor = colors.primary,
            )
        }
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            SmallSpacing()
            Header(profile = profile)
            SmallSpacing()
            HorizontalPager(
                state = highlightState,
                horizontalAlignment = Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                RecipeCard()
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
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(horizontal = 10.dp),
            ) {
                items(10) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            painter = rememberImagePainter(
                                data = "https://avatars.githubusercontent.com/u/47350130?v=4",
                                builder = {
                                    transformations(CircleCropTransformation())
                                }
                            ),
                            contentDescription = "chef avatar",
                            modifier = Modifier
                                .size(75.dp)
                                .clip(shapes.large)
                                .clickable { }
                        )
                        Text("moose")
                    }
                }
            }
            SmallSpacing()
            Text(
                text = "Recipes",
                modifier = Modifier.padding(10.dp),
                style = typography.h5.copy(color = colors.primary)
            )
            ScrollableTabRow(
                edgePadding = 0.dp,
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
            RecipeItems(type = titles[recipesState.currentPage])
        }
    }
}