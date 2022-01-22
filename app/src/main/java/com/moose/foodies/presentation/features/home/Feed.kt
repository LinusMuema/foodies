package com.moose.foodies.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.moose.foodies.R
import com.moose.foodies.presentation.components.CenterColumn
import com.moose.foodies.presentation.components.ScrollableColumn
import com.moose.foodies.presentation.components.SmallSpace
import com.moose.foodies.presentation.components.TinySpace
import com.moose.foodies.util.customTabIndicatorOffset
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
fun Feed(controller: NavController){
    val viewmodel: HomeViewmodel = hiltViewModel()

    val chefs by viewmodel.chefs.observeAsState()
    val recipes by viewmodel.recipes.observeAsState()
    val profile by viewmodel.profile.observeAsState()
    val refreshing by viewmodel.refreshing.observeAsState()

    var counter by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        (0..5).map {
            counter = it
            delay(1000)
        }
    }

    val coroutineScope = rememberCoroutineScope()

    val height = LocalConfiguration.current.screenHeightDp
    val container = (height * .7).dp

    // loading animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cooking))

    SwipeRefresh(
        state =  rememberSwipeRefreshState(refreshing!!),
        onRefresh = { viewmodel.refresh() },
        indicator = { state, trigger -> Indicator(state, trigger)}
    ) {
        ScrollableColumn {
            SmallSpace()
            profile?.let { Header(profile = it) }
            SmallSpace()


            if (counter >= 5 && !recipes.isNullOrEmpty()) {
                val highlights = recipes!!.filter { recipe -> recipe.type == viewmodel.type }
                val highlightState = rememberPagerState(initialPage = 0)
                HorizontalPager(
                    count = highlights.size,
                    state = highlightState,
                    contentPadding = PaddingValues(end = 125.dp),
                ) {
                    RecipeCard(controller, highlights[it])
                }
                SmallSpace()
                Text(
                    text = "Discover Chefs",
                    modifier = Modifier.padding(10.dp),
                    style = typography.h5.copy(color = colors.primary)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp),
                ) {
                    items(chefs!!){
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
                                    .clickable {
                                        controller.navigate("/chef/${it._id}")
                                    }
                            )
                            TinySpace()
                            Text(it.username, style = typography.body1)
                        }
                    }
                }

                SmallSpace()
                Text(
                    text = "Recipes",
                    modifier = Modifier.padding(10.dp),
                    style = typography.h5.copy(color = colors.primary)
                )

                val pagerState = rememberPagerState(initialPage = 0)
                val titles = mutableListOf("Breakfast", "Snacks", "Main", "Others")
                titles.remove(viewmodel.type)

                val items = recipes!!.filter { recipe -> recipe.type == titles[pagerState.currentPage]}

                TabRow(
                    backgroundColor = Color.Transparent,
                    selectedTabIndex = pagerState.currentPage,
                    divider = {},
                    indicator = { positions ->
                        TabRowDefaults.Indicator(
                            height = 7.5.dp,
                            color = colors.secondary,
                            modifier = Modifier.customTabIndicatorOffset(positions[pagerState.currentPage])
                        )
                    }
                ) {
                    titles.forEachIndexed { index, title ->
                        val color = if (pagerState.currentPage == index) colors.secondary else colors.onSurface
                        Tab(
                            selected = false,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } }) {
                            Text(title, color = color, modifier = Modifier.padding(15.dp))
                        }
                    }
                }
                TinySpace()
                RecipeItems(controller, items)
                HorizontalPager(count = titles.size, state = pagerState){}
            } else {
                CenterColumn(modifier = Modifier.height(container)){
                    Text(
                        text = "Getting you some recipes...",
                        style = typography.body1.copy(color = colors.onSurface)
                    )
                    TinySpace()
                    LottieAnimation(
                        composition = composition,
                        iterations = Int.MAX_VALUE,
                        modifier = Modifier.size(250.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Indicator(state: SwipeRefreshState, trigger: Dp) {
    SwipeRefreshIndicator(
        scale = true,
        state = state,
        contentColor = colors.primary,
        refreshTriggerDistance = trigger,
        backgroundColor = colors.background,
    )
}
