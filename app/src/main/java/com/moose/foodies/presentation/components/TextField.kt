package com.moose.foodies.presentation.components

import android.util.Patterns
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Ascii
import androidx.compose.ui.text.input.KeyboardType.Companion.Number
import androidx.compose.ui.text.input.KeyboardType.Companion.Password
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moose.foodies.presentation.theme.grey200
import com.moose.foodies.presentation.theme.smallVPadding
import com.moose.foodies.presentation.theme.tinyVPadding
import com.moose.foodies.util.getTextFieldColors
import java.util.regex.Pattern
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors as Colors

open class TextFieldState(
    initial: String = "",
    onChanged: (String) -> Unit = {},
    val validators: List<Validators>,
){

    var text: String by mutableStateOf(initial)
    var message: String by mutableStateOf("")
    var hasError: Boolean by mutableStateOf(false)

    fun onChanged(value: String){
        text = value
        hideError()
    }

    fun showError(error: String){
        hasError = true
        message = error
    }

    fun hideError(){
        message = ""
        hasError = false
    }

    fun clear() = "".also { text = it }

    fun validate(): Boolean {
        return validators.map {
            when (it){
                is Email -> {
                    if (!email()) showError(it.message)
                    email()
                }
                is Required -> {
                    if (!required()) showError(it.message)
                    required()
                }
                is Regex -> {
                    if (!regex(it.regex)) showError("value does not match required regex")
                    regex(it.regex)
                }
                is Max -> {
                    if (!max(it.limit)) showError("value cannot be more than ${it.limit}")
                    max(it.limit)
                }
                is Min -> {
                    if (!min(it.limit)) showError("value cannot be less than ${it.limit}")
                    min(it.limit)
                }
            }
        }.all { it }
    }


    private fun required(): Boolean = text.isNotEmpty()

    private fun max(limit: Double): Boolean = text.toDouble() > limit

    private fun min(limit: Double): Boolean = text.toDouble() < limit

    private fun email(): Boolean = Patterns.EMAIL_ADDRESS.matcher(text).matches()

    private fun regex(regex: String): Boolean = Pattern.compile(regex).matcher(text).matches()
}

@Composable
fun OutlinedInput(
    label: String?,
    type: KeyboardType,
    hide: Boolean = false,
    state: TextFieldState,
    modifier: Modifier = Modifier,
    onChanged: (String) -> Unit = {}
){
    val color = grey200.copy(alpha = .2f)
    var hidden by remember { mutableStateOf(hide) }

    val icon = if (hidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    val transformation = if (hidden)  PasswordVisualTransformation() else VisualTransformation.None

    val colors = Colors(
        backgroundColor = color,
        trailingIconColor = color,
        errorBorderColor = Transparent,
        focusedBorderColor = Transparent,
        unfocusedBorderColor = Transparent,
        focusedLabelColor = colors.onPrimary,
        unfocusedLabelColor = colors.onPrimary,
    )

    Column(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
        if (label !=  null) Text(label, modifier = Modifier.padding(5.dp))
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
                    state.hasError -> Icon(imageVector = Icons.Filled.Error, contentDescription = "Error")
                    type == KeyboardType.Password -> {
                        IconButton(onClick = { hidden = !hidden }) {
                            Icon(imageVector  = icon, "visibility")
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
fun TextInput(state: TextFieldState, modifier: Modifier = Modifier, maxLines: Int = 1){
    Column {
        TextField(
            value = state.text,
            modifier = modifier,
            maxLines = maxLines,
            shape = shapes.small,
            isError = state.hasError,
            onValueChange = { state.onChanged(it) },
            colors = MaterialTheme.getTextFieldColors(),
            keyboardOptions = Default.copy(keyboardType = Ascii),
        )
        if (state.hasError) {
            Text(state.message, color = colors.error, modifier = modifier.tinyVPadding())
        }
    }
}

@Composable
fun PasswordInput(state: TextFieldState, modifier: Modifier = Modifier){
    var hidden by remember { mutableStateOf(true) }
    val icon = if (hidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    val transformation = if (hidden)  PasswordVisualTransformation() else VisualTransformation.None

    Column {
        TextField(
            maxLines = 1,
            value = state.text,
            modifier = modifier,
            shape = shapes.small,
            isError = state.hasError,
            visualTransformation = transformation,
            onValueChange = { state.onChanged(it) },
            colors = MaterialTheme.getTextFieldColors(),
            keyboardOptions = Default.copy(keyboardType = Password),
            trailingIcon = {
                when {
                    state.hasError -> Icon(imageVector = Icons.Filled.Error, contentDescription = "Error")
                    else -> IconButton(onClick = { hidden = !hidden }) {
                        Icon(imageVector  = icon, "visibility")
                    }
                }
            },
        )
        if (state.hasError) {
            Text(state.message, color = colors.error, modifier = modifier.tinyVPadding())
        }
    }
}

@Composable
fun NumberInput(state: TextFieldState, modifier: Modifier = Modifier){
    Column {
        TextField(
            maxLines = 1,
            value = state.text,
            modifier = modifier,
            shape = shapes.small,
            isError = state.hasError,
            onValueChange = { state.onChanged(it) },
            colors = MaterialTheme.getTextFieldColors(),
            keyboardOptions = Default.copy(keyboardType = Number),
        )
        if (state.hasError) {
            Text(state.message, color = colors.error, modifier = modifier.tinyVPadding())
        }
    }
}