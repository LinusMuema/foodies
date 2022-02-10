package com.moose.foodies.presentation.features.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Email
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.moose.foodies.presentation.components.*
import com.moose.foodies.presentation.theme.largeHPadding
import com.moose.foodies.presentation.theme.smallVPadding

@Composable
fun Signup(){
    val viewmodel: AuthViewmodel = hiltViewModel()
    val loading by remember { viewmodel.loading }

    val formState = remember { viewmodel.signupFormState }

    val emailState = formState.getState("email")
    val confirmState = formState.getState("confirm")
    val passwordState = formState.getState("password")

    val inputModifier = Modifier.fillMaxWidth().largeHPadding()
    val labelModifier = Modifier.fillMaxWidth().largeHPadding().smallVPadding()

    Column(horizontalAlignment = CenterHorizontally) {
        Text(text = "Don't have an account?")
        Text(text = "Enter your details below to get started")

        SmallSpace()

        Text(text = "Email address", modifier = labelModifier, textAlign = TextAlign.Start)
        TextInput(state = emailState, modifier = inputModifier, type = Email)

        SmallSpace()

        Text(text = "Password", modifier = labelModifier, textAlign = TextAlign.Start)
        PasswordInput(state = passwordState, modifier = inputModifier)

        SmallSpace()

        Text(text = "Confirm password", modifier = labelModifier, textAlign = TextAlign.Start)
        PasswordInput(state = confirmState, modifier = inputModifier)

        MediumSpace()

        FilledButton(text = "Sign up", modifier = inputModifier, loading = loading) {
            val matches = confirmState.text != passwordState.text
            when {
                matches -> confirmState.showError("passwords do not match")
                formState.validate() && !loading -> viewmodel.signup()
            }
        }

        SmallSpace()

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            SmallSpace()
            TextButton(text = "Back to login", onClick = { viewmodel.changeScreen(0) })
        }
    }
}