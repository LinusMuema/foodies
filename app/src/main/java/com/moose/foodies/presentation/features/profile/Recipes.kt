package com.moose.foodies.presentation.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import com.moose.foodies.presentation.components.NetImage

@ExperimentalCoilApi
@Composable
fun Recipes(controller: NavHostController) {
    val viewmodel: ProfileViewmodel = hiltViewModel()
    val items by viewmodel.recipes.observeAsState()

    items?.let {
        LazyColumn {
            items(it){
                Card(
                    elevation = 5.dp,
                    modifier = Modifier.fillMaxWidth().height(175.dp).padding(10.dp)
                ) {

                    Box(modifier = Modifier.fillMaxSize().clickable { controller.navigate("/recipe/${it._id}") }){
                        NetImage(url = it.image, modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}