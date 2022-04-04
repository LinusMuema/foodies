package com.moose.foodies.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions.Companion.Default
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Ascii
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsc.form_builder.BaseState
import com.moose.foodies.presentation.theme.grey200
import com.moose.foodies.presentation.theme.tinyVPadding
import com.moose.foodies.util.getTextFieldColors
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors as Colors

// TODO: All instances to be replaced
@Composable
fun OutlinedInput(
    label: String?,
    type: KeyboardType,
    hide: Boolean = false,
    state: TextFieldState<*>,
    modifier: Modifier = Modifier,
    onChanged: (String) -> Unit = {}
) {
    val color = grey200.copy(alpha = .2f)
    var hidden by remember { mutableStateOf(hide) }

    val icon = if (hidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    val transformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None

    val colors = Colors(
        backgroundColor = color,
        trailingIconColor = color,
        errorBorderColor = Transparent,
        focusedBorderColor = Transparent,
        unfocusedBorderColor = Transparent,
        focusedLabelColor = colors.onPrimary,
        unfocusedLabelColor = colors.onPrimary,
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp)) {
        if (label != null) Text(label, modifier = Modifier.padding(5.dp))
        TextField(
            colors = colors,
            value = state.text,
            shape = shapes.small,
            isError = state.hasError,
            modifier = modifier.fillMaxWidth(),
            visualTransformation = transformation,
            textStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
            keyboardOptions = Default.copy(keyboardType = type),
            onValueChange = {
                onChanged(it)
                state.text = it
                state.hideError()
            },
            trailingIcon = {
                when {
                    state.hasError -> Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "Error"
                    )
                    type == KeyboardType.Password -> {
                        IconButton(onClick = { hidden = !hidden }) {
                            Icon(imageVector = icon, "visibility")
                        }
                    }
                }
            },
        )
        if (state.hasError) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(state.message, color = MaterialTheme.colors.error)
            }
        }
    }
}

@Composable
fun TextInput(
    state: com.dsc.form_builder.TextFieldState,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    type: KeyboardType = Ascii
) {
    Column {
        TextField(
            value = state.value,
            modifier = modifier,
            maxLines = maxLines,
            shape = shapes.small,
            isError = state.hasError,
            onValueChange = { state.change(it) },
            colors = MaterialTheme.getTextFieldColors(),
            keyboardOptions = Default.copy(keyboardType = type),
        )
        if (state.hasError) {
            Text(state.errorMessage, color = colors.error, modifier = modifier.tinyVPadding())
        }
    }
}

@Composable
fun PasswordInput(state: com.dsc.form_builder.TextFieldState, modifier: Modifier = Modifier) {
    var hidden by remember { mutableStateOf(true) }
    val icon = if (hidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    val transformation = if (hidden) PasswordVisualTransformation() else VisualTransformation.None

    Column {
        TextField(
            maxLines = 1,
            value = state.value,
            modifier = modifier,
            shape = shapes.small,
            isError = state.hasError,
            visualTransformation = transformation,
            onValueChange = { state.change(it) },
            colors = MaterialTheme.getTextFieldColors(),
            keyboardOptions = Default.copy(keyboardType = Password),
            trailingIcon = {
                when {
                    state.hasError -> Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "Error"
                    )
                    else -> IconButton(onClick = { hidden = !hidden }) {
                        Icon(imageVector = icon, "visibility")
                    }
                }
            },
        )
        if (state.hasError) {
            Text(state.errorMessage, color = colors.error, modifier = modifier.tinyVPadding())
        }
    }
}