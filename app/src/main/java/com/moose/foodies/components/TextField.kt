package com.moose.foodies.components

import android.util.Log
import android.util.Patterns
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
import java.util.regex.Pattern
import androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors as Colors

open class TextFieldState (private val validators: List<Validators>){

    var message: String by mutableStateOf("")
    var text: String by mutableStateOf("")
    var hasError: Boolean by mutableStateOf(false)

    private fun showError(error: String){
        hasError = true
        message = error
    }

    fun hideError(){
        message = ""
        hasError = false
    }

    fun validate() {
        println(validators)
        for (validator in validators){
            when (validator){
                is Email -> if (!email()) showError(validator.message)
                is Required -> if (!required()) showError(validator.message)
                is Regex -> if (!regex(validator.regex)) showError("value does not match required regex")
                is Max -> if (!max(validator.limit)) showError("value cannot be more than ${validator.limit}")
                is Min -> if (!min(validator.limit)) showError("value cannot be less than ${validator.limit}")
            }
        }
    }

    private fun required(): Boolean = text.isNotEmpty()

    private fun max(limit: Double): Boolean = text.toDouble() > limit

    private fun min(limit: Double): Boolean = text.toDouble() < limit

    private fun email(): Boolean = Patterns.EMAIL_ADDRESS.matcher(text).matches()

    private fun regex(regex: String): Boolean = Pattern.compile(regex).matcher(text).matches()
}

@Composable
fun OutlinedInput(label: String, type: KeyboardType, hide: Boolean = false, state: TextFieldState){
    val color = MaterialTheme.colors.onPrimary
    var hidden by remember { mutableStateOf(hide) }


    val icon = if (hidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
    val modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp, vertical = 5.dp)
    val transformation = if (hidden)  PasswordVisualTransformation() else VisualTransformation.None
    val colors = Colors(cursorColor = color, focusedLabelColor = color, focusedBorderColor = color)

    Column {
        OutlinedTextField(
            colors = colors,
            value = state.text,
            modifier = modifier,
            label = { Text(label) },
            isError = state.hasError,
            visualTransformation = transformation,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = type),
            onValueChange = {
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
                MediumSpacing()
                Text(state.message, color = MaterialTheme.colors.error)
            }
        }
    }
}