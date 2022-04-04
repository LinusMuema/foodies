package com.moose.foodies.presentation.features.add

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
import com.dsc.form_builder.SelectState
import com.google.accompanist.flowlayout.FlowRow
import com.moose.foodies.presentation.theme.shapes

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
fun Categories(state: SelectState) {

    fun select(item: String) {
        if (state.value.contains(item)) state.unselect(item)
        else state.select(item)
    }

    FlowRow {
        categories.forEach {
            var text = colors.primary
            var background = colors.background

            if (state.value.contains(it)) {
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

                Text(
                    text = it,
                    style = typography.body1.copy(color = text),
                    modifier = Modifier.padding(15.dp, 5.dp)
                )
            }
        }
    }
}