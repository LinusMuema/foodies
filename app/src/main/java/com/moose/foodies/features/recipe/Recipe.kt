package com.moose.foodies.features.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moose.foodies.util.getActivity

@Composable
fun Recipe(id: String?) {
    val viewmodel: RecipeViewmodel = hiltViewModel()

    val context = LocalContext.current
    val window = context.getActivity()!!.window
    WindowCompat.setDecorFitsSystemWindows(window, false)

    // set status bar colors
    val color = Color.Black.copy(alpha = .1f)
    val controller = rememberSystemUiController()
    SideEffect { controller.setSystemBarsColor(color = color) }

    val recipe by viewmodel.recipe.observeAsState()
    val equipment by viewmodel.equipment.observeAsState()
    val ingredients by viewmodel.ingredients.observeAsState()

    id?.let { viewmodel.getRecipe(it) }

    recipe?.let{
        val painter = rememberImagePainter(data = it.image, builder = { crossfade(true) })
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop,
            painter = painter,
            contentDescription = "${it.name} image"
        )
    }
}