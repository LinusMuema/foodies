package com.moose.foodies.presentation.features.chef

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.presentation.components.TinySpace

@Composable
@ExperimentalFoundationApi
fun Recipes(controller: NavController, recipes: List<Recipe>) {
    val arrangement = Arrangement.SpaceBetween

    // create the gradient
    val variant = MaterialTheme.colors.primaryVariant
    val colors = listOf(Transparent, Transparent, Transparent, variant)
    val gradient = Brush.verticalGradient(colors = colors)


    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(recipes) {
            val boxModifier = Modifier.fillMaxSize().background(brush = gradient).padding(10.dp).clickable { controller.navigate("/recipe/${it._id}") }

            Card(
                elevation = 5.dp,
                modifier = Modifier.height(250.dp).padding(10.dp),
                content = {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        painter = rememberImagePainter(data = it.image),
                        contentDescription = "${it.name} image"
                    )
                    Box(modifier = boxModifier) {
                        Column(Modifier.fillMaxSize(), arrangement) {
                            TinySpace()
                            Text(
                                maxLines = 1,
                                text = it.name,
                                fontWeight = FontWeight.Medium,
                                overflow = TextOverflow.Ellipsis,
                                style = typography.h6.copy(color = White)
                            )
                        }
                    }
                }
            )
        }
    }
}