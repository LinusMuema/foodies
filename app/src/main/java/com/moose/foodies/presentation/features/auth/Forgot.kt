package com.moose.foodies.presentation.features.auth

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
import com.moose.foodies.presentation.components.*

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
        Column(modifier = Modifier.padding(20.dp, 10.dp)) {
            OutlinedInput(
                state = emailState,
                label = "Email address",
                type = KeyboardType.Email,
            )
        }
        FilledButton(text = "Submit", size = .9f, loading = loading) {
            emailState.validate()
            if (!emailState.hasError && !loading) {
                viewmodel.forgot(emailState.text)
            }
        }
        SmallSpace()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            SmallSpace()
            TextButton(text = "Back to login", onClick = { viewmodel.changeScreen(0) })
        }
    }
}