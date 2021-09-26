package com.moose.foodies.features.auth

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.moose.foodies.R
import com.moose.foodies.components.*
import com.moose.foodies.features.navigation.NavigationActivity
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.util.onError
import com.moose.foodies.util.onSuccess
import com.moose.foodies.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalPagerApi
class AuthActivity : ComponentActivity() {
    private val viewmodel: AuthViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun Content
                (){
        val screen by viewmodel.screen.observeAsState(0)

        viewmodel.result.observe(this, {
            viewmodel.changeLoading(false)

            it.onError { error -> toast(error) }
            it.onSuccess {
                startActivity(Intent(this, NavigationActivity::class.java))
                finish()
            }
        })

        FoodiesTheme {
            Surface(color = MaterialTheme.colors.primary) {
                CenterColumn {
                    Icon(
                        modifier = Modifier.size(150.dp),
                        contentDescription = "splash icon",
                        tint = MaterialTheme.colors.secondary,
                        painter = painterResource(id = R.drawable.ic_chef),
                    )
                    when (screen){
                        0 -> Login()
                        1 -> Signup()
                        2 -> Forgot()
                    }
                }
            }
        }
    }

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun Login(){
        val rowArrangement = Arrangement.SpaceEvenly
        val loading by viewmodel.loading.observeAsState(false)
        val passwordState = remember { TextFieldState(validators = listOf(Required())) }
        val emailState = remember { TextFieldState(validators = listOf(Email(), Required())) }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Before we get cookin`")
            Text(text = "We need to verify your identity.")
            SmallSpacing()

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
            SmallSpacing()
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

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun Forgot(){
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

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
    @Composable
    private fun Signup(){
        val loading by viewmodel.loading.observeAsState(false)
        val confirmState = remember { TextFieldState(validators = listOf(Required())) }
        val passwordState = remember { TextFieldState(validators = listOf(Required())) }
        val emailState = remember { TextFieldState(validators = listOf(Email(), Required())) }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Don't have an account?")
            Text(text = "Enter your details below to get started")
            SmallSpacing()
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
            OutlinedInput(
                hide = true,
                state = confirmState,
                label = "Confirm password",
                type = KeyboardType.Password,
            )
            SmallSpacing()
            FilledButton(text = "Sign up", size = 0.85f, loading = loading) {
                emailState.validate()
                confirmState.validate()
                passwordState.validate()

                if (confirmState.text != passwordState.text){
                    confirmState.showError("passwords do not match")
                } else if (!passwordState.hasError && !emailState.hasError && !loading){
                    viewmodel.signup(emailState.text, passwordState.text)
                }
            }
            SmallSpacing()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                SmallSpacing()
                TextButton(text = "Back to login", onClick = { viewmodel.changeScreen(0) })
            }
        }
    }
    
}