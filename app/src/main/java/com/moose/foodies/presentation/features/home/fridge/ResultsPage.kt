package com.moose.foodies.presentation.features.home.fridge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.moose.foodies.domain.models.Item
import com.moose.foodies.presentation.components.NetImage
import com.moose.foodies.presentation.components.SmallSpace
import com.moose.foodies.presentation.components.TinySpace
import com.moose.foodies.presentation.theme.*

@Composable
@ExperimentalCoilApi
fun ResultsPage(onOpen: () -> Unit, controller: NavController){
    val viewmodel: FridgeViewmodel = hiltViewModel()
    val recipes by remember { viewmodel.recipes }
    val selected by remember { viewmodel.selected }
    val ingredients by remember { viewmodel.ingredients }

    Box(modifier = Modifier.fillMaxSize()){
        Column {
            SmallSpace()
            Text(
                text = "What you can cook",
                style = typography.h5.copy(color = colors.primary)
            )
            SmallSpace()
            LazyColumn {
                items(recipes){ recipe ->
                    val missing = recipe.ingredients
                        .filter { id -> !selected.map { it._id }.contains(id) }
                        .map { id -> ingredients.first { it._id == id } }

                    Card(modifier = Modifier.smallVPadding().clickable { controller.navigate("/recipe/${recipe._id}") }) {
                        Box(modifier = Modifier.fillMaxWidth().background(colors.background)){
                            Column {
                                NetImage(
                                    url = recipe.image,
                                    modifier = Modifier.fillMaxWidth().height(175.dp)
                                )
                                Column(modifier = Modifier.smallPadding()){
                                    Text(
                                        text = recipe.name,
                                        style = typography.h6.copy(fontSize = 16.sp),
                                    )
                                    Text(text = "What you're missing:")
                                }
                                MissingItems(items = missing)
                                TinySpace()
                            }
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(onClick = { onOpen() }) {
                Icon(Icons.Default.FilterList, contentDescription = "fab icon")
            }
        }
    }
}

@Composable
@ExperimentalCoilApi
fun MissingItems(items: List<Item>){
    LazyRow {
        items(items){
            Box(modifier = Modifier.tinyHPadding()) {
                NetImage(url = it.image, modifier = Modifier.size(65.dp).clip(CircleShape))
            }
        }
    }
}