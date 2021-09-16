package com.moose.foodies.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedInput(text: String, label: String, onChanged: (value: String) -> Unit){
    val color = MaterialTheme.colors.onPrimary
    val colors = outlinedTextFieldColors(focusedBorderColor = color, focusedLabelColor = color)

    OutlinedTextField(
        value = text,
        colors = colors,
        label = { Text(label) },
        onValueChange = onChanged,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 5.dp),
    )
}