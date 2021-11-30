package com.moose.foodies.presentation.features.recipe

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moose.foodies.R
import com.moose.foodies.presentation.components.TinySpacing
import com.moose.foodies.presentation.features.recipe.ui.Details
import com.moose.foodies.presentation.theme.FoodiesTheme
import com.moose.foodies.presentation.theme.shapes
import com.moose.foodies.presentation.theme.typography

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun Recipe(id: String?, controller: NavHostController) {
    val viewmodel: RecipeViewmodel = hiltViewModel()

    val isDark = colors.isLight
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = Transparent, darkIcons = isDark)
    }

    FoodiesTheme {
        val recipe by viewmodel.recipe.observeAsState()
        val favorite by viewmodel.favorite.observeAsState()
        val equipment by viewmodel.equipment.observeAsState()
        val ingredients by viewmodel.ingredients.observeAsState()
        val height = LocalConfiguration.current.screenHeightDp

        id?.let {
            viewmodel.getRecipe(it)
            viewmodel.checkFavorite(it)
        }

        ingredients?.let{
            val painter = rememberImagePainter(data = recipe!!.image, builder = { crossfade(true) })
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

            val topPadding = (height * .3).dp
            val sheetHeight = (height * .7).dp
            val imageHeight = (height * ((1f - fraction) * .4)).dp

            val buttonColor = Color.Gray.copy(alpha = .8f)
            val icon = if (favorite!!) R.drawable.ic_favorites_filled else R.drawable.ic_favorites

            ProvideWindowInsets {
                BottomSheetScaffold(
                    scaffoldState = bottomSheet,
                    sheetPeekHeight = sheetHeight,
                    sheetBackgroundColor = colors.background,
                    sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
                    sheetContent = { Details(fraction, recipe!!, ingredients!!, equipment!!) },
                ) {
                    Surface(color = colors.background) {
                        Box(modifier = Modifier.fillMaxHeight()) {
                            Image(
                                painter = painter,
                                contentScale = ContentScale.Crop,
                                contentDescription = "${recipe!!.name} image",
                                modifier = Modifier.animateContentSize().fillMaxWidth().height(height = imageHeight),
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth().systemBarsPadding()
                            ) {
                                Box(modifier = Modifier.padding(10.dp).clip(shapes.large).background(buttonColor).clickable { controller.popBackStack() }) {
                                    Icon(
                                        modifier = Modifier.size(30.dp).padding(5.dp),
                                        painter = painterResource(id = R.drawable.ic_back),
                                        contentDescription = "back icon",
                                        tint = Color.White,
                                    )
                                }

                                if (recipe!!.type != "PERSONAL")
                                    Box(modifier = Modifier.padding(10.dp).clip(shapes.large).background(buttonColor).clickable { viewmodel.toggleFavorite() }) {
                                        Icon(
                                            modifier = Modifier.size(30.dp).padding(7.dp),
                                            painter = painterResource(id = icon),
                                            contentDescription = "favorites icon",
                                            tint = Color.White,
                                        )
                                }
                            }

                            Box(modifier = Modifier.align(Alignment.TopEnd).padding(top = topPadding, end = 10.dp).clip(shapes.large).background(buttonColor)){
                                Row(modifier = Modifier.padding(5.dp), verticalAlignment = CenterVertically) {
                                    TinySpacing()
                                    Icon(
                                        tint = Color.White,
                                        contentDescription = "time",
                                        modifier = Modifier.size(20.dp),
                                        painter = painterResource(id = R.drawable.ic_clock),
                                    )
                                    TinySpacing()
                                    Text(recipe!!.time, style = typography.body1.copy(color = Color.White))
                                    TinySpacing()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}