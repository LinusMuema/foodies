package com.moose.foodies.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
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
    message: String = "",
    toggle: Boolean = true,
    hasError: Boolean = false,
    onChanged: (value: String) -> Unit,
    type: KeyboardType = KeyboardType.Text,
    togglePass: (value: Boolean) -> Unit = {},
){
    val color = MaterialTheme.colors.onPrimary
    val icon = if (toggle) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    val transformation = if (toggle) VisualTransformation.None else PasswordVisualTransformation()

    Column {
        OutlinedTextField(
            value = text,
            isError = hasError,
            label = { Text(label) },
            onValueChange = onChanged,
            visualTransformation = transformation,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = type),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 5.dp),
            colors = outlinedTextFieldColors(
                cursorColor = color,
                focusedLabelColor = color,
                focusedBorderColor = color
            ),
            trailingIcon = {
                when {
                    hasError -> Icon(imageVector = Icons.Filled.Error, contentDescription = "Error")
                    type == KeyboardType.Password -> {
                        IconButton(onClick = { togglePass(!toggle) }) {
                            Icon(imageVector  = icon, "visibility")
                        }
                    }
                }
            },
        )
        if (hasError) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                SmallSpacing()
                Text(message, color = MaterialTheme.colors.error)
            }
        }
    }
}