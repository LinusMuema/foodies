package com.moose.foodies.presentation.features.home.fridge

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.flowlayout.FlowColumn
import com.google.accompanist.flowlayout.FlowRow
import com.moose.foodies.domain.models.Item
import com.moose.foodies.presentation.components.NetImage
import com.moose.foodies.presentation.components.SmallSpace
import com.moose.foodies.presentation.components.TinySpace
import com.moose.foodies.presentation.theme.smallPadding
import com.moose.foodies.presentation.theme.smallVPadding

@Composable
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun Fridge(){
    val viewmodel: FridgeViewmodel = hiltViewModel()
    val ingredients by remember { viewmodel.ingredients }

    var open by remember { mutableStateOf(true) }

    val spec = slideInVertically() + fadeIn() with slideOutVertically() + fadeOut()
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)){
        AnimatedContent(targetState = open, transitionSpec = { spec }) {
            if (it) FilterPage(onClose = { open = false }, ingredients = ingredients)
            else ResultsPage(onOpen = { open = true })
        }
    }
}

@Composable
@ExperimentalCoilApi
@ExperimentalFoundationApi
fun FilterPage(onClose: () -> Unit, ingredients: List<Item>){
    val chars = ('A'..'Z').toList()
    Box(modifier = Modifier.fillMaxSize()){
        Column {
            SmallSpace()
            Text(
                text = "What's in your fridge?",
                style = typography.h5.copy(color = colors.primary)
            )
            TinySpace()
            LazyColumn {
                items(chars){
                    val items = ingredients.filter { item -> item.name.first() == it }
                    FilterSection(section = it, ingredients = items)
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(onClick = { onClose() }) {
                Icon(Icons.Default.Search, contentDescription = "fab icon")
            }
        }
    }
}

@Composable
@ExperimentalCoilApi
@ExperimentalFoundationApi
fun FilterSection(section: Char, ingredients: List<Item>){
    val width = LocalConfiguration.current.screenWidthDp / 2.1
    Column(modifier = Modifier.smallVPadding()) {
        Text(
            text = section.uppercase(),
            style = typography.h5.copy(color = colors.primary)
        )
        FlowRow {
            for (item in ingredients){
                Card(
                    elevation = 5.dp,
                    shape = shapes.small,
                    modifier = Modifier.width(width.dp).padding(10.dp, 5.dp),
                    content = {
                        Box(modifier = Modifier.fillMaxSize().background(colors.background)) {
                            Row {
                                NetImage(url = item.image, modifier = Modifier.size(60.dp).smallPadding())
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ResultsPage(onOpen: () -> Unit){
    Box(modifier = Modifier.fillMaxSize().padding(10.dp)){
        Column {
            SmallSpace()
            Text(
                text = "what you can cook",
                style = typography.h5.copy(color = colors.primary)
            )
        }
        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            FloatingActionButton(onClick = { onOpen() }) {
                Icon(Icons.Default.Add, contentDescription = "fab icon")
            }
        }
    }
}