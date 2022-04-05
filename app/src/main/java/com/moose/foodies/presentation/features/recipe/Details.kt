package com.moose.foodies.presentation.features.recipe

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.moose.foodies.R
import com.moose.foodies.domain.models.CompleteRecipe
import com.moose.foodies.presentation.components.TinySpace
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.util.customTabIndicatorOffset
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Details(fraction: Float, recipe: CompleteRecipe, profileClick: () -> Unit) {

    val top = ((fraction + 1f) * 20)
    val context = LocalContext.current
    val scroll = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val titles = listOf("Description", "Ingredients", "Equipment", "Steps")

    Column(modifier = Modifier.fillMaxSize().verticalScroll(state = scroll)) {
        Row(modifier = Modifier.padding(10.dp, top.dp), verticalAlignment = CenterVertically) {
            Image(
                painter = rememberImagePainter(
                    data = recipe.user.avatar,
                    builder = { transformations(CircleCropTransformation()) }
                ),
                contentDescription = "chef avatar",
                modifier = Modifier.size(50.dp).clip(shapes.large).clickable { profileClick() }
            )
            TinySpace()
            Column {
                Text(recipe.name, style = typography.h5.copy(color = colors.primary))
                Text("@${recipe.user.username}")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                val message = "Do you know how to make ${recipe.name}? \nCheck out the recipe on foodies! \uD83D\uDE0B \uD83C\uDF73 \n\n"
                val url = "http://foodies.moose.ac/recipes?id=${recipe.id}"
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "$message$url")
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)
            }) {
                Icon(
                    contentDescription = "share icon",
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_share),
                )
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
                    Text(title, color = color, modifier = Modifier.padding(10.dp))
                }
            }
        }
        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top, count = 4) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)) {
                when (it) {
                    0 -> Description(description = recipe.description)
                    1 -> Items(items = recipe.ingredients)
                    2 -> Items(items = recipe.equipment)
                    3 -> Steps(steps = recipe.steps)
                }
            }
        }
    }
}

@Composable
fun Description(description: String){
    Text(description, modifier = Modifier.padding(5.dp))
}

@Composable
fun Items(items: List<Item>){
    FlowRow {
        items.forEach {
            Column(modifier = Modifier.padding(10.dp), horizontalAlignment = CenterHorizontally) {
                Image(
                    painter = rememberImagePainter(data = it.image),
                    contentDescription = "${it.name} image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shapes.large)
                )
                TinySpace()
                Text(it.name)
            }
        }
    }
}

@Composable
fun Steps(steps: List<String>){
    Column {
        steps.mapIndexed { i, s ->
            Row(modifier = Modifier.padding(5.dp)) {
                Text("${i+1}.")
                TinySpace()
                Text(s)
            }
        }
    }
}