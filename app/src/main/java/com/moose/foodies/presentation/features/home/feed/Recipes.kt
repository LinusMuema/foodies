package com.moose.foodies.presentation.features.home.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.presentation.components.TinySpace
import com.moose.foodies.util.customTabIndicatorOffset
import kotlinx.coroutines.launch


@Composable
@ExperimentalPagerApi
fun Recipes(controller: NavController, viewmodel: FeedViewmodel, recipes: List<Recipe>) {
    val state = rememberPagerState(initialPage = 0)
    val titles = mutableListOf("Breakfast", "Snacks", "Main", "Others")
    titles.remove(viewmodel.type)

    val items = recipes.filter { recipe -> recipe.type == titles[state.currentPage] }

    Text(
        text = "Recipes",
        modifier = Modifier.padding(10.dp),
        style = typography.h5.copy(color = colors.primary)
    )

    TabRow(
        divider = {},
        backgroundColor = Transparent,
        selectedTabIndex = state.currentPage,
        indicator = { Indicator(state, it) },
        tabs = { titles.forEachIndexed { i, t -> PagerTab(t, state, i) } }
    )

    TinySpace()
    RecipeItems(controller, items)
    HorizontalPager(count = titles.size, state = state) {}
}

@Composable
@ExperimentalPagerApi
fun PagerTab(title: String, state: PagerState, index: Int) {
    val current = state.currentPage
    val coroutineScope = rememberCoroutineScope()
    fun scroll() {
        coroutineScope.launch { state.animateScrollToPage(index) }
    }

    Tab(selected = false, onClick = { scroll() }) {
        Text(
            text = title,
            modifier = Modifier.padding(15.dp),
            color = if (current == index) colors.secondary else colors.onSurface,
        )
    }
}

@Composable
@ExperimentalPagerApi
fun Indicator(state: PagerState, positions: List<TabPosition>) {
    TabRowDefaults.Indicator(
        height = 7.5.dp,
        color = colors.secondary,
        modifier = Modifier.customTabIndicatorOffset(positions[state.currentPage])
    )
}

@Composable
fun RecipeItems(controller: NavController, recipes: List<Recipe>) {
    LazyRow {
        items(recipes) { recipe ->
            val arrangement = Arrangement.SpaceBetween

            // create the gradient
            val variant = colors.primaryVariant
            val colors = listOf(Transparent, Transparent, Transparent, variant)
            val gradient = Brush.verticalGradient(colors = colors)

            val cardModifier =  Modifier.width(200.dp).height(250.dp).padding(10.dp)
            val boxModifier = Modifier.fillMaxSize().background(brush = gradient).padding(10.dp).clickable { controller.navigate("/recipe/${recipe._id}") }

            Card(
                elevation = 5.dp,
                modifier = cardModifier,
                content = {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "${recipe.name} image",
                        painter = rememberImagePainter(data = recipe.image),
                    )
                    Box(
                        modifier = boxModifier,
                        content = {
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = arrangement) {
                                TinySpace()
                                Text(
                                    maxLines = 1,
                                    text = recipe.name,
                                    fontWeight = FontWeight.Medium,
                                    overflow = TextOverflow.Ellipsis,
                                    style = typography.h6.copy(color = Color.White)
                                )
                            }
                        }
                    )
                }
            )
        }
    }
}