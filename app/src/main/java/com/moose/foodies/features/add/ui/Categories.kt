package com.moose.foodies.features.add.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import com.moose.foodies.theme.shapes

val categories = listOf(
    "Snack",
    "Breakfast",
    "Lunch",
    "Dinner",
    "Drink",
    "Baking",
    "Salad",
    "Appetizer",
    "Dessert"
)

@Composable
fun Categories(onChange: (List<String>) -> Unit) {
    var items by remember { mutableStateOf(listOf<String>()) }

    fun addItem(item: String) = (items + listOf(item)).also { items = it }
    fun removeItem(item: String) = (items - listOf(item)).also { items = it }
    fun select(item: String) {
        if (items.contains(item)) removeItem(item) else addItem(item)
        onChange(items)
    }

    FlowRow {
        categories.forEach {
            var text = colors.primary
            var background = colors.background

            if (items.contains(it)) {
                text = colors.onPrimary
                background = colors.primary
            }

            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .border(1.dp, colors.primary, shapes.large)
                    .clip(shapes.large)
                    .background(background)
                    .clickable { select(it) }) {

                Text(text = it, style = typography.body1.copy(color = text), modifier = Modifier.padding(15.dp, 5.dp))
            }
        }
    }
}