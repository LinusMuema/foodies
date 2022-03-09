package com.moose.foodies.presentation.features.home.fridge

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.moose.foodies.domain.models.Item
import com.moose.foodies.presentation.components.NetImage
import com.moose.foodies.presentation.components.SmallSpace
import com.moose.foodies.presentation.components.TinySpace
import com.moose.foodies.presentation.theme.*

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
    Column(modifier = Modifier.smallVPadding()) {
        Text(
            text = section.uppercase(),
            style = typography.h5.copy(color = colors.primary)
        )
        for (index in ingredients.indices step 2){
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Box(modifier = Modifier.weight(1f)) {
                    ItemCard(item = ingredients[index])
                }
                if (index != ingredients.size-1) {
                    Box(modifier = Modifier.weight(1f)) {
                        ItemCard(item = ingredients[index + 1])
                    }
                } else Box(modifier = Modifier.weight(1f)) // equalise the weight
            }
        }
    }
}

@Composable
@ExperimentalCoilApi
fun ItemCard(item: Item){
    val viewmodel: FridgeViewmodel = hiltViewModel()
    val selected by remember { viewmodel.selected }

    val color = if (selected.contains(item)) colors.onPrimary else colors.onSurface
    val background = if (selected.contains(item)) colors.primary else colors.surface

    Card(
        elevation = 5.dp,
        shape = shapes.small,
        modifier = Modifier.tinyPadding(),
        content = {
            Box(modifier = Modifier.fillMaxSize().background(background).clickable { viewmodel.setIngredient(item) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    NetImage(url = item.image, modifier = Modifier.size(60.dp).smallPadding())
                    TinySpace()
                    Text(text = item.name, style = typography.body1.copy(color = color))
                }
            }
        }
    )
}