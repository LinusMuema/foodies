package com.moose.foodies.presentation.features.home.fridge

import android.util.Log
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
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterList
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
import com.moose.foodies.presentation.theme.tinyPadding

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