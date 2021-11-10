package com.moose.foodies.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moose.foodies.R
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.moose.foodies.components.CenterColumn
import com.moose.foodies.components.TinySpacing

@Composable
fun Recipes(controller: NavHostController) {
    val viewmodel: ProfileViewmodel = hiltViewModel()
    val items by viewmodel.recipes.observeAsState()
    items?.let {
        LazyColumn {
            items(it){
                val arrangement = Arrangement.SpaceBetween
                val timeGray = Color.Gray.copy(.8f)

                // create the gradient
                val painter = rememberImagePainter(
                    data = it.image,
                    builder = { crossfade(true) }
                )
                val variant = colors.primaryVariant
                val colors = listOf(Color.Transparent, Color.Transparent, Color.Transparent, variant)
                val gradient = Brush.verticalGradient(colors = colors)

                Box(modifier = Modifier.padding(10.dp)){
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp), elevation = 5.dp) {
                        when (painter.state){
                            is ImagePainter.State.Loading -> {
                                val resource = if (isSystemInDarkTheme()) R.raw.pulse_dark else R.raw.pulse_light
                                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resource))
                                CenterColumn {
                                    LottieAnimation(
                                        composition = composition,
                                        iterations = Int.MAX_VALUE,
                                        modifier = Modifier.width(75.dp),
                                    )
                                }
                            }
                        }

                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                            painter = painter,
                            contentDescription = "${it.name} image"
                        )

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .background(brush = gradient)
                            .clickable { controller.navigate("/recipe/${it._id}") }
                            .padding(10.dp)){
                            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = arrangement) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = arrangement) {
                                    Box(modifier = Modifier
                                        .clip(shapes.medium)
                                        .background(timeGray)
                                        .padding(5.dp)) {
                                        Row (verticalAlignment = CenterVertically){
                                            TinySpacing()
                                            Icon(
                                                tint = Color.White,
                                                contentDescription = "time",
                                                modifier = Modifier.size(14.dp),
                                                painter = painterResource(id = R.drawable.ic_clock)
                                            )
                                            TinySpacing()
                                            Text(it.time, color = Color.White, fontSize = 14.sp)
                                            TinySpacing()
                                        }
                                    }
                                }
                                Box(modifier = Modifier.padding(10.dp)){
                                    Text(
                                        text = it.name,
                                        style = typography.h6.copy(color = Color.White),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}