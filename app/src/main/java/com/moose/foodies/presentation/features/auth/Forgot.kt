package com.moose.foodies.presentation.features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moose.foodies.presentation.components.*
import com.moose.foodies.presentation.theme.largeHPadding
import com.moose.foodies.presentation.theme.smallVPadding

@Composable
fun Forgot(){
    val viewmodel: AuthViewmodel = hiltViewModel()
    val loading by viewmodel.loading.observeAsState(false)

    val emailState = remember { TextFieldState(validators = listOf(Email(), Required())) }

    val inputModifier = Modifier.fillMaxWidth().largeHPadding()
    val labelModifier = Modifier.fillMaxWidth().largeHPadding().smallVPadding()

    Column(horizontalAlignment = CenterHorizontally) {
        Text(text = "Forgot your password?")
        Text(text = "Don't worry. We'll send you a reset link")

        SmallSpace()

        Text(text = "Email address", modifier = labelModifier, textAlign = TextAlign.Start)
        TextInput(state = emailState, modifier = inputModifier, type = KeyboardType.Email)

        MediumSpace()

        FilledButton(text = "Submit", modifier = inputModifier, loading = loading) {
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