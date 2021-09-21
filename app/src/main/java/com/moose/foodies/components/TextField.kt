package com.moose.foodies.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.moose.foodies.components.Validation.EMAIL
import com.moose.foodies.components.Validation.REQUIRED
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors as Colors

@Composable
fun OutlinedInput(
    label: String,
    type: KeyboardType,
    hide: Boolean = false, // initially hide the input contents
    validation: List<Validation>,
){
    val color = MaterialTheme.colors.onPrimary

    var hidden by remember { mutableStateOf(hide) }
    var value by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    val icon = if (hidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    val modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 5.dp)
    val transformation = if (hidden)  PasswordVisualTransformation() else VisualTransformation.None
    val colors = Colors(cursorColor = color, focusedLabelColor = color, focusedBorderColor = color)

    Column {
        OutlinedTextField(
            value = value,
            colors = colors,
            modifier = modifier,
            label = { Text(label) },
            onValueChange = { value = it },
            visualTransformation = transformation,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = type),
            trailingIcon = {
                when {
                    hasError -> Icon(imageVector = Icons.Filled.Error, contentDescription = "Error")
                    type == KeyboardType.Password -> {
                        IconButton(onClick = { hidden = !hidden }) {
                            Icon(imageVector  = icon, "visibility")
                        }
                    }
                }
            },
        )
        if (hasError) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                MediumSpacing()
                Text(message, color = MaterialTheme.colors.error)
            }
        }
    }

    fun value(): String = value // get the form value

    fun showError(error: String){
        hasError = true
        message = error
    }

    fun validate() {
        lateinit var validators: Validators
        for (validator in validation){
            when (validator){
                EMAIL -> if (!validators.email(value)) showError("invalid email address")
                REQUIRED -> if (!validators.email(value)) showError("this field is required")
            }
        }
    }
}