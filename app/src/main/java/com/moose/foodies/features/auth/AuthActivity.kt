package com.moose.foodies.features.auth

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moose.foodies.R
import com.moose.foodies.components.CenterColumn
import com.moose.foodies.components.FilledButton
import com.moose.foodies.components.OutlinedInput
import com.moose.foodies.components.SmallSpacing
import com.moose.foodies.theme.FoodiesTheme

class AuthActivity : ComponentActivity() {
    private val viewmodel: AuthViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Screen() }
    }

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun Screen(){
        FoodiesTheme {
            Surface(color = MaterialTheme.colors.primary) {
                CenterColumn {
                    Icon(
                        modifier = Modifier.size(150.dp),
                        contentDescription = "splash icon",
                        tint = MaterialTheme.colors.secondary,
                        painter = painterResource(id = R.drawable.ic_chef),
                    )
                    Login()
                }
            }
        }
    }

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun Login(){
        val rowArrangement = Arrangement.SpaceEvenly
        val email by viewmodel.email.observeAsState("")
        val toggle by viewmodel.toggle.observeAsState(false)
        val password by viewmodel.password.observeAsState("")

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Before we get cookin`")
            Text(text = "We need to verify your identity.")
            SmallSpacing()
            OutlinedInput(
                text = email,
                label = "Email address",
                type = KeyboardType.Email,
                onChanged = { viewmodel.changeEmail(it) }
            )
            OutlinedInput(
                toggle = toggle,
                text = password,
                label = "Password",
                type = KeyboardType.Password,
                togglePass = { viewmodel.changeToggle(it) },
                onChanged = { viewmodel.changePassword(it) }
            )
            SmallSpacing()
            FilledButton(text = "Login", size = 0.85f) {}
            SmallSpacing()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = rowArrangement) {
               Text("Sign up")
               Text("Forgot password")
            }
        }
    }
    
}