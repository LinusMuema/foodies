package com.moose.foodies.features.recipe.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.moose.foodies.components.TinySpacing
import com.moose.foodies.models.Item
import com.moose.foodies.models.Recipe
import com.moose.foodies.util.customTabIndicatorOffset
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Details(fraction: Float, recipe: Recipe, ingredients: List<Item>, equipment: List<Item>) {

    val top = ((fraction + .1f) * 40) - 10
    Log.d("Value", "Details: value is $top and fraction is $fraction")
    val scroll = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 4)
    val titles = listOf("Description", "Ingredients", "Equipment", "Steps")

    Column(modifier = Modifier.fillMaxSize().verticalScroll(state = scroll)) {
        Row(modifier = Modifier.padding(10.dp, 40.dp), verticalAlignment = CenterVertically) {
            Image(
                painter = rememberImagePainter(
                    data = recipe.user.avatar,
                    builder = { transformations(CircleCropTransformation()) }
                ),
                contentDescription = "chef avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(shapes.large)
                    .clickable { }
            )
            TinySpacing()
            Column {
                Text(recipe.name, style = typography.h5.copy(color = colors.primary))
                Text("@${recipe.user.username}")
            }

        }
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
    }
}