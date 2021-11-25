package com.moose.foodies.features.recipe.ui

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moose.foodies.features.recipe.RecipeViewmodel
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.util.getActivity

@ExperimentalMaterialApi
@Composable
fun Recipe(id: String?) {
    val viewmodel: RecipeViewmodel = hiltViewModel()

    FoodiesTheme {

        val context = LocalContext.current
        val window = context.getActivity()!!.window
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // set status bar colors
        val controller = rememberSystemUiController()
        SideEffect { controller.setSystemBarsColor(color = Transparent) }

        val data by viewmodel.recipe.observeAsState()
        val height = LocalConfiguration.current.screenHeightDp

        id?.let { viewmodel.getRecipe(it) }

        data?.let{ recipe ->
            val painter = rememberImagePainter(data = recipe.image, builder = { crossfade(true) })
            val bottomSheet = rememberBottomSheetScaffoldState(bottomSheetState = rememberBottomSheetState(Collapsed))

            val progress = bottomSheet.bottomSheetState.progress.fraction
            val targetValue = bottomSheet.bottomSheetState.targetValue
            val currentValue = bottomSheet.bottomSheetState.currentValue

            val fraction =  when {
                currentValue == Collapsed && targetValue == Collapsed -> 0f
                currentValue == Expanded && targetValue == Expanded -> 1f
                currentValue == Collapsed && targetValue == Expanded -> progress
                else -> 1f - progress
            }

            val sheetHeight = (height * .7).dp
            val imageHeight = (height * ((1f - fraction) * .4)).dp

            BottomSheetScaffold(
                scaffoldState = bottomSheet,
                sheetContent = { Details() },
                sheetPeekHeight = sheetHeight,
                sheetBackgroundColor = colors.background,
                sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            ) {
                Surface(color = colors.background) {
                    Box(modifier = Modifier.fillMaxHeight()) {
                        Image(
                            painter = painter,
                            contentScale = ContentScale.Crop,
                            contentDescription = "${recipe.name} image",
                            modifier = Modifier.animateContentSize().fillMaxWidth().height(height = imageHeight),
                        )
                    }
                }
            }
        }
    }
}