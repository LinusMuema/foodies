package com.moose.foodies.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.moose.foodies.R

@Composable
fun RecipeItems(type: String){
    LazyRow {
        items(10){
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
                    painter = painterResource(id = R.drawable.sample),
                    contentDescription = "sample"
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(brush = gradient)
                    .clickable { }
                    .padding(10.dp)){
                    Column(Modifier.fillMaxSize(), arrangement) {
                        Box(
                            Modifier
                                .align(Alignment.End)
                                .clip(MaterialTheme.shapes.large)
                                .background(timeGray)
                                .padding(7.5.dp)) {
                            Icon(
                                tint = Color.White,
                                contentDescription = "time",
                                modifier = Modifier.size(18.dp),
                                painter = painterResource(id = R.drawable.ic_favorites)
                            )
                        }
                        Text(
                            maxLines = 1,
                            text = "$type pancakes",
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.h6.copy(color = Color.White)
                        )
                    }
                }
            }
        }
    }
}