package com.moose.foodies.presentation.features.home.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.presentation.components.MediumSpace
import com.moose.foodies.presentation.components.NetImage
import com.moose.foodies.presentation.components.SmallSpace

@Composable
@ExperimentalCoilApi
fun Explore(controller: NavController) {
    val viewmodel: ExploreViewmodel = hiltViewModel()
    val recipes = viewmodel.recipes.collectAsLazyPagingItems()

    val categories by remember { viewmodel.categories }
    viewmodel.getRecipes("")

    Column {
        Text(
            text = "Discover more recipes",
            style = typography.h5.copy(color = colors.primary)
        )
        SmallSpace()

        LazyColumn {
            items(recipes) {
                Box(modifier = Modifier.clickable { controller.navigate("/recipe/${it!!._id}") }) {
                    Recipe(it!!)
                }
            }
        }
    }
}

@Composable
@ExperimentalCoilApi
fun Recipe(recipe: Recipe) {
    val ingredients = recipe.ingredients.size
    Card(elevation = 10.dp, modifier = Modifier.padding(10.dp).background(colors.background)) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(shapes.medium)
        ) {
            Row {
                NetImage(modifier = Modifier.width(200.dp), url = recipe.image)
                SmallSpace()
                Column {
                    MediumSpace()
                    Text(
                        text = recipe.name,
                        fontWeight = FontWeight.Medium,
                        style = typography.h6.copy(color = Color.White),
                    )
                    SmallSpace()
                    Text(text = "$ingredients ingredients")
                    Text(text = "Ready in ${recipe.time}")
                }
            }
        }
    }
}