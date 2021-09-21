package com.moose.foodies.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors as Colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.moose.foodies.components.Validation.*
import java.util.regex.Pattern
import javax.inject.Inject


@Inject lateinit var validators: Validators

@Composable
fun OutlinedInput(
    label: String,
    type: KeyboardType,
    toggle: Boolean = false,
    validation: List<Validation>,
    togglePass: (value: Boolean) -> Unit = {}
){
    val color = MaterialTheme.colors.onPrimary

    var value by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    val icon = if (toggle) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
    val modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp, vertical = 5.dp)
    val transformation = if (toggle) VisualTransformation.None else PasswordVisualTransformation()
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
                        IconButton(onClick = { togglePass(!toggle) }) {
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

    fun value(): String = value

    fun showError(error: String){
        hasError = true
        message = error
    }

    fun validate() {
        for (validator in validation){
            when (validator){
                EMAIL -> if (!validators.email(value)) showError("invalid email address")
                REQUIRED -> if (!validators.email(value)) showError("this field is required")
            }
        }
    }
}