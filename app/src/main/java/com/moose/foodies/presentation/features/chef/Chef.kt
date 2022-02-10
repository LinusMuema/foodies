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
import androidx.compose.runtime.remember
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
import com.moose.foodies.util.CHEF_TIMEOUT

@Composable
@ExperimentalMaterialApi
@ExperimentalFoundationApi
fun Chef(id: String?, controller: NavHostController) {
    val viewmodel: ChefViewmodel = hiltViewModel()
    viewmodel.getChef(id)

    val chef by remember { viewmodel.chef }
    val recipes by remember { viewmodel.recipes }
    val counter by remember { viewmodel.seconds }
    val count = if (recipes.isNullOrEmpty()) 0 else recipes!!.size

    FoodiesTheme {
        Status()
        ProvideWindowInsets {
            if (counter >= CHEF_TIMEOUT && chef != null) {
                Scaffold(
                    modifier = Modifier.systemBarsPadding(),
                    floatingActionButton = { Fab(chef!!) },
                    content = {
                        Column(modifier = Modifier.fillMaxSize()) {
                            TinySpace()
                            IconButton(onClick = { controller.popBackStack() }) {
                                Icon(
                                    contentDescription = "back icon",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(5.dp),
                                    painter = painterResource(id = R.drawable.ic_back),
                                )
                            }
                            SmallSpace()
                            Header(chef = chef!!, count = count)
                            SmallSpace()
                            recipes?.let { Recipes(controller, it) }
                        }
                    }
                )
            } else Loading()
        }
    }
}

@Composable
fun Loading(){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.chef))

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

@Composable
fun Fab(profile: Profile) {
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            val message = "Have you seen ${profile.username}'s recipes on Foodies? \uD83D\uDE0B \nGo ahead and check out their amazing recipes! \uD83D\uDC4C \n\n"
            val url = "http://foodies.moose.ac/chefs?id=${profile._id}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "$message$url")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        },
        content = {
            Icon(
                contentDescription = "share icon",
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_share),
            )
        }
    )
}