package com.moose.foodies.presentation.features.home.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.MainAxisAlignment.Center
import com.google.accompanist.flowlayout.MainAxisAlignment.Start
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.presentation.components.NetImage


@Composable
@ExperimentalCoilApi
@ExperimentalFoundationApi
fun Recipes(recipes: List<Recipe>, controller: NavHostController) {

    FlowRow(
        mainAxisAlignment = Center,
        lastLineMainAxisAlignment = Start,
        modifier = Modifier.fillMaxWidth(),
    ) {
        recipes.forEach {
            val path = "/recipe/${it._id}"
            val boxStyle = Modifier
                .fillMaxSize()
                .clickable { controller.navigate(path) }
            val cardStyle = Modifier
                .fillMaxWidth(.475f)
                .height(200.dp)
                .padding(10.dp)

            Card(elevation = 5.dp, modifier = cardStyle) {
                Box(modifier = boxStyle) {
                    NetImage(url = it.image, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

}