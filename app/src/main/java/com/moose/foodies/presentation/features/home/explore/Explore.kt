package com.moose.foodies.presentation.features.home.explore

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.presentation.components.MediumSpace
import com.moose.foodies.presentation.components.NetImage
import com.moose.foodies.presentation.components.SmallSpace
import com.moose.foodies.presentation.theme.smallPadding

@Composable
@ExperimentalCoilApi
fun Explore(controller: NavController) {
    val viewmodel: ExploreViewmodel = hiltViewModel()

    val recipes by remember { viewmodel.recipes }

    Column {
        SmallSpace()
        Text(
            text = "Discover more recipes",
            style = typography.h5.copy(color = colors.primary)
        )
        SmallSpace()

        LazyColumn {
            items(recipes) {
                val isEven = recipes.indexOf(it) % 2 == 0
                Box(modifier = Modifier.clickable { controller.navigate("/recipe/${it._id}") }) {
                    Recipe(recipe = it, reverse = isEven)
                }
            }
        }
    }
}

@Composable
@ExperimentalCoilApi
fun Recipe(recipe: Recipe, reverse: Boolean) {
    val alignment = if (reverse) Alignment.End else Alignment.Start
    val direction = if (reverse) LayoutDirection.Rtl else LayoutDirection.Ltr

    Box(modifier = Modifier
        .fillMaxWidth()
        .smallPadding()) {
        val time = "Ready in ${recipe.time}"
        val ingredients = "${recipe.ingredients.size} ingredients "
        val image: @Composable () -> Unit = {
            NetImage(
                url = recipe.image,
                modifier = Modifier
                    .size(175.dp, 200.dp)
                    .clip(shapes.medium)
            )
        }

        val text: @Composable () -> Unit = {
            Column(horizontalAlignment = alignment) {
                MediumSpace()
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Medium,
                    style = typography.h6.copy(color = Color.White),
                )
                SmallSpace()
                Text(text = ingredients)
                Text(text = time)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = SpaceBetween,
            verticalAlignment = CenterVertically,
            content = {
                if (reverse) {
                    image()
                    SmallSpace()
                    text()
                    SmallSpace()
                } else {
                    text()
                    SmallSpace()
                    image()
                    SmallSpace()
                }
            }
        )
    }
}