package com.moose.foodies.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedInput(
    text: String,
    label: String,
    toggle: Boolean = true,
    onChanged: (value: String) -> Unit,
    type: KeyboardType = KeyboardType.Text,
    togglePass: (value: Boolean) -> Unit = {},
){
    val color = MaterialTheme.colors.onPrimary
    val icon = if (toggle) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    val colors = outlinedTextFieldColors(focusedBorderColor = color, focusedLabelColor = color)
    val transformation = if (toggle) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        value = text,
        colors = colors,
        label = { Text(label) },
        onValueChange = onChanged,
        trailingIcon = {
            if (type == KeyboardType.Password) {
                IconButton(onClick = { togglePass(!toggle) }) {
                    Icon(imageVector  = icon, "visibility")
                }
            }
        },
        visualTransformation = transformation,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = type),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 5.dp),
    )
}