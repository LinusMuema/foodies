package com.moose.foodies.presentation.features.chef

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.moose.foodies.R
import com.moose.foodies.domain.models.Profile
import com.moose.foodies.presentation.components.SmallSpacing
import com.moose.foodies.presentation.components.TinySpacing
import com.moose.foodies.presentation.theme.FoodiesTheme
import com.moose.foodies.presentation.theme.shapes
import com.moose.foodies.presentation.theme.typography

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun Chef(id: String?, controller: NavHostController) {
    val viewmodel: ChefViewmodel = hiltViewModel()
    viewmodel.getChef(id)

    val chef by viewmodel.chef.observeAsState()
    val recipes by viewmodel.recipes.observeAsState()
    val count = if (recipes.isNullOrEmpty()) 0 else recipes!!.size

    chef?.let { profile ->
        FoodiesTheme {
            ProvideWindowInsets {
                Scaffold(modifier = Modifier.systemBarsPadding(), floatingActionButton = { Fab(profile) }) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TinySpacing()
                        IconButton(onClick = { controller.popBackStack() }) {
                            Icon(
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(5.dp),
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "back icon",
                            )
                        }
                        SmallSpacing()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            TinySpacing()
                            Image(
                                painter = rememberImagePainter(
                                    data = profile.avatar,
                                    builder = { transformations(CircleCropTransformation()) }
                                ),
                                contentDescription = "user avatar",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                            )
                            TinySpacing()
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
                                SmallSpacing()
                                Text(profile.username, style = typography.h5.copy(color = colors.primary))
                                TinySpacing()
                                Text(profile.description, textAlign = TextAlign.Center)
                            }
                            TinySpacing()
                        }
                    }
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
        val url = "http://foodies.moose.ac/chefs/${profile._id}"
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