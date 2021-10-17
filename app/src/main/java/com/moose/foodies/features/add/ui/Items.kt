package com.moose.foodies.features.add.ui

import androidx.compose.runtime.*
import com.moose.foodies.components.TextDropdown
import com.moose.foodies.features.add.AddViewmodel
import com.moose.foodies.models.Item

@Composable
fun Items(viewmodel: AddViewmodel, type: String, onClick: (Item) -> Unit) {

    var items by remember { mutableStateOf(viewmodel.getItems("", type)) }

    TextDropdown(
        items = items,
        onClick = onClick,
        label = "${type}s",
        onChange = { items = viewmodel.getItems(it, type) }
    )
}