package com.moose.foodies.features.auth.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moose.foodies.components.*
import com.moose.foodies.features.auth.AuthViewmodel

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Login(){
    val viewmodel: AuthViewmodel = hiltViewModel()
    val rowArrangement = Arrangement.SpaceEvenly
    val loading by viewmodel.loading.observeAsState(false)
    val passwordState = remember { TextFieldState(validators = listOf(Required())) }
    val emailState = remember { TextFieldState(validators = listOf(Email(), Required())) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Before we get cookin`")
        Text(text = "We need to verify your identity.")
        Column(modifier = Modifier.padding(20.dp, 10.dp)) {
            OutlinedInput(
                state = emailState,
                label = "Email address",
                type = KeyboardType.Email,
            )
            OutlinedInput(
                hide = true,
                label = "Password",
                state = passwordState,
                type = KeyboardType.Password,
            )
        }
        FilledButton(text = "Login", loading = loading, size = 0.85f) {
            emailState.validate()
            passwordState.validate()

            if (!emailState.hasError && !passwordState.hasError && !loading){
                viewmodel.login(emailState.text, passwordState.text)
            }
        }
        SmallSpacing()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = rowArrangement) {
            TextButton(text = "Sign up", onClick = { viewmodel.changeScreen(1) })
            TextButton(text = "Forgot password", onClick = { viewmodel.changeScreen(2) })
        }
    }
}