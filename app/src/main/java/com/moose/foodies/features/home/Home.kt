package com.moose.foodies.features.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.moose.foodies.components.SmallSpacing
import kotlinx.coroutines.launch

@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = UI_MODE_NIGHT_YES)
fun Home(){
    val viewmodel: HomeViewmodel = hiltViewModel()
    val profile = viewmodel.profile.observeAsState().value
    val highlightState = rememberPagerState(pageCount = 10,  initialOffscreenLimit = 2)


    val coroutineScope = rememberCoroutineScope()
    val recipesState = rememberPagerState(pageCount = 5)
    val titles = listOf("New", "Breakfast", "Lunch", "Supper", "Snacks")

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
        ){
            Text(
                text = "Discover Chefs",
                style = typography.h5.copy(fontWeight = SemiBold, color = colors.onSurface)
            )
            Text(
                text = "See all",
                style = typography.body1.copy(color = colors.secondaryVariant)
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(horizontal = 10.dp),
        ) {
            items(10){
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
            style = typography.h5.copy(fontWeight = SemiBold, color = colors.onSurface)
        )
        ScrollableTabRow(
            edgePadding = 0.dp,
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
                val interactionSource = remember { MutableInteractionSource() }
                val color = if (recipesState.currentPage == index) colors.secondary else colors.onPrimary
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) { coroutineScope.launch { recipesState.animateScrollToPage(index) } }
                        .background(colors.primary)
                        .padding(10.dp, 10.dp)
                ){
                    Text(title, color = color)
                }
            }
        }
        RecipeItems(type = titles[recipesState.currentPage])
    }
}

fun Modifier.customTabIndicatorOffset(position: TabPosition): Modifier {
    return composed(debugInspectorInfo { position }) {
        val indicatorWidth = 7.5.dp
        val currentTabWidth = position.width
        val indicatorOffset by animateDpAsState(
            targetValue = position.left + currentTabWidth / 2 - indicatorWidth / 2,
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )
        fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
            .offset(x = indicatorOffset)
            .width(indicatorWidth)
            .clip(shapes.large)
    }
}