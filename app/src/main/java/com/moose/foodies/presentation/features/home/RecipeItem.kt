package com.moose.foodies.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.moose.foodies.R
import com.moose.foodies.domain.models.Recipe

@Composable
fun RecipeItems(controller: NavController, recipes: List<Recipe>){
    LazyRow {
        items(recipes){ recipe ->
            val arrangement = Arrangement.SpaceBetween
            val timeGray = Color.Gray.copy(.8f)

            // create the gradient
            val variant = colors.primaryVariant
            val colors = listOf(Color.Transparent, Color.Transparent, Color.Transparent, variant)
            val gradient = Brush.verticalGradient(colors = colors)

            Card(modifier = Modifier.width(200.dp).height(250.dp).padding(10.dp), elevation = 5.dp){
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    painter = rememberImagePainter(data = recipe.image),
                    contentDescription = "${recipe.name} image"
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(brush = gradient)
                    .clickable {  controller.navigate("/recipe/${recipe._id}")}
                    .padding(10.dp)){
                    Column(Modifier.fillMaxSize(), arrangement) {
                        Box(
                            Modifier.align(Alignment.End).clip(shapes.large).background(timeGray).padding(7.5.dp)) {
                            Icon(
                                tint = White,
                                contentDescription = "favorite",
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(id = R.drawable.ic_favorites)
                            )
                        }
                        Text(
                            maxLines = 1,
                            text = recipe.name,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.h6.copy(color = White)
                        )
                    }
                }
            }
        }
    }
}