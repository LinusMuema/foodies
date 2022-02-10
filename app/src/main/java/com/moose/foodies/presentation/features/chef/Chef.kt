package com.moose.foodies.presentation.features.chef

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moose.foodies.R
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.presentation.components.CenterColumn
import com.moose.foodies.presentation.components.SmallSpace
import com.moose.foodies.presentation.components.TinySpace
import com.moose.foodies.presentation.theme.FoodiesTheme
import com.moose.foodies.presentation.theme.Status
import com.moose.foodies.presentation.theme.typography

@ExperimentalFoundationApi
@Composable
@OptIn(ExperimentalMaterialApi::class)
fun Chef(id: String?, controller: NavHostController) {
    val viewmodel: ChefViewmodel = hiltViewModel()
    viewmodel.getChef(id)

    val chef by viewmodel.chef.observeAsState()
    val recipes by viewmodel.recipes.observeAsState()
    val count = if (recipes.isNullOrEmpty()) 0 else recipes!!.size

    // loading animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chef))


    FoodiesTheme {
        Status()
        ProvideWindowInsets {
            if (chef != null){
                Scaffold(modifier = Modifier.systemBarsPadding(), floatingActionButton = { Fab(chef!!) }) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TinySpace()
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(
                                modifier = Modifier.size(30.dp).padding(5.dp),
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "back icon",
                            )
                        }
                        SmallSpace()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            TinySpace()
                            Image(
                                painter = rememberImagePainter(
                                    data = chef!!.avatar,
                                    builder = { transformations(CircleCropTransformation()) }
                                ),
                                contentDescription = "user avatar",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )
                            TinySpace()
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = count.toString(),
                                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold)
                                        )
                                        Text("Recipes", style = MaterialTheme.typography.h6.copy(fontSize = 16.sp))
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("0", style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold))
                                        Text("Likes", style = MaterialTheme.typography.h6.copy(fontSize = 16.sp))
                                    }
                                }
                                SmallSpace()
                                Text(chef!!.username, style = typography.h5.copy(color = colors.primary))
                                TinySpace()
                                Text(chef!!.description, textAlign = TextAlign.Center)
                            }
                            TinySpace()
                        }
                        SmallSpace()
                        recipes?.let { items ->
                            val arrangement = Arrangement.SpaceBetween
                            val timeGray = Color.Gray.copy(.8f)

                            // create the gradient
                            val variant = colors.primaryVariant
                            val colors = listOf(Color.Transparent, Color.Transparent, Color.Transparent, variant)
                            val gradient = Brush.verticalGradient(colors = colors)
                            LazyVerticalGrid(cells = GridCells.Fixed(2)) {
                                items(items){
                                    Card(modifier = Modifier.height(250.dp).padding(10.dp), elevation = 5.dp){
                                        Image(
                                            modifier = Modifier.fillMaxWidth(),
                                            contentScale = ContentScale.Crop,
                                            painter = rememberImagePainter(data = it.image),
                                            contentDescription = "${it.name} image"
                                        )
                                        Box(modifier = Modifier
                                            .fillMaxSize()
                                            .background(brush = gradient)
                                            .clickable {  controller.navigate("/recipe/${it._id}")}
                                            .padding(10.dp)){
                                            Column(Modifier.fillMaxSize(), arrangement) {
                                                TinySpace()
                                                Text(
                                                    maxLines = 1,
                                                    text = it.name,
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
                    }
                }
            } else {
                CenterColumn {
                    Text(
                        text = "Getting the chef...",
                        style = typography.body1.copy(color = colors.onSurface)
                    )
                    TinySpace()
                    LottieAnimation(
                        composition = composition,
                        iterations = Int.MAX_VALUE,
                        modifier = Modifier.size(250.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Fab(profile: Profile) {
    val context = LocalContext.current

    FloatingActionButton(onClick = {
        val message = "Have you seen ${profile.username}'s recipes on Foodies? \uD83D\uDE0B \nGo ahead and check out their amazing recipes! \uD83D\uDC4C \n\n"
        val url = "http://foodies.moose.ac/chefs?id=${profile._id}"
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