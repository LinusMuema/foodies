package com.moose.foodies.presentation.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.moose.foodies.R.drawable.ic_clock
import com.moose.foodies.R.drawable.sample
import com.moose.foodies.presentation.components.TinySpacing

@Composable
@ExperimentalCoilApi
fun RecipeCard(){
    val arrangement = SpaceBetween
    val timeGray = Gray.copy(.8f)

    // create the gradient
    val variant = colors.primaryVariant
    val colors = listOf(Transparent, Transparent, Transparent, variant)
    val gradient = Brush.verticalGradient(colors = colors)

    Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp)){
        Card(modifier = Modifier.width(300.dp).height(175.dp), elevation = 5.dp) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = Crop,
                painter = painterResource(id = sample),
                contentDescription = "sample"
            )
            Box(modifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
                .clickable {  }
                .padding(10.dp)){
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = arrangement) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = arrangement) {
                        Box(modifier = Modifier.clip(shapes.medium).background(timeGray).padding(5.dp)) {
                            Row (verticalAlignment = Alignment.CenterVertically){
                                TinySpacing()
                                Icon(
                                    tint = White,
                                    contentDescription = "time",
                                    modifier = Modifier.size(14.dp),
                                    painter = painterResource(id = ic_clock)
                                )
                                TinySpacing()
                                Text("30 mins", color = White, fontSize = 14.sp)
                                TinySpacing()
                            }
                        }
                        Box(modifier = Modifier
                            .clickable { }
                            .clip(shapes.large)
                            .background(White)
                            .padding(2.5.dp)){
                            Image(
                                painter = rememberImagePainter(
                                    data = "https://avatars.githubusercontent.com/u/47350130?v=4",
                                    builder = {
                                        transformations(CircleCropTransformation())
                                    }
                                ),
                                contentDescription = "chef avatar",
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                    Text(
                        text = "Pancakes",
                        style = typography.h6.copy(color = White),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}