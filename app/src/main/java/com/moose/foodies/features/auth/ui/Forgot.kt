package com.moose.foodies.features.auth.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.moose.foodies.components.*
import com.moose.foodies.features.auth.AuthViewmodel

@Preview(name = "Light Theme")
@Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Forgot(){
    val viewmodel: AuthViewmodel = hiltViewModel()
    val loading by viewmodel.loading.observeAsState(false)
    val emailState = remember { TextFieldState(validators = listOf(Email(), Required())) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Forgot your password?")
        Text(text = "Don't worry. We'll send you a reset link")
        SmallSpacing()
        OutlinedInput(
            state = emailState,
            label = "Email address",
            type = KeyboardType.Email,
        )
        SmallSpacing()
        FilledButton(text = "Submit", size = 0.85f, loading = loading) {
            emailState.validate()
            if (!emailState.hasError && !loading) {
                viewmodel.forgot(emailState.text)
            }
        }
        SmallSpacing()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            SmallSpacing()
            TextButton(text = "Back to login", onClick = { viewmodel.changeScreen(0) })
        }
    }
}