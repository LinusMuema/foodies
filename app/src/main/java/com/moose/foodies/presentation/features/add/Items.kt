package com.moose.foodies.presentation.features.add

import androidx.compose.runtime.*
import com.moose.foodies.presentation.components.TextDropdown
import com.moose.foodies.presentation.features.add.AddViewmodel
import com.moose.foodies.domain.models.Item

@Composable
fun Items(viewmodel: AddViewmodel, type: String, onClick: (Item) -> Unit) {

    var items by remember { mutableStateOf(viewmodel.getItems("", type)) }

    TextDropdown(
        label = type,
        items = items,
        onClick = onClick,
        onChange = { items = viewmodel.getItems(it, type) }
    )
}