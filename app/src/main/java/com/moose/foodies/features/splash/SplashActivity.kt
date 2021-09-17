package com.moose.foodies.features.splash

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moose.foodies.R
import com.moose.foodies.components.CenterColumn
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.features.navigation.NavigationActivity
import com.moose.foodies.theme.FoodiesTheme
import com.moose.foodies.util.Preferences
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

@AndroidEntryPoint
@ExperimentalAnimationApi
class SplashActivity : ComponentActivity() {

    @Inject lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Screen() }
        redirect()
    }

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun Screen(){
        FoodiesTheme {
            Surface(color = MaterialTheme.colors.primary) {
                CenterColumn {
                    val animation = fadeIn(animationSpec = tween(2000))
                    val visible by remember { mutableStateOf(true) }
                    AnimatedVisibility(visible = visible, enter = animation) {
                        Icon(
                            modifier = Modifier.size(150.dp),
                            contentDescription = "splash icon",
                            tint = MaterialTheme.colors.secondary,
                            painter = painterResource(id = R.drawable.ic_chef),
                        )
                    }
                }
            }
        }
    }

    private fun redirect(){
        val navigate = timerTask {
            // get the token
            val token = preferences.getToken()
            val context = this@SplashActivity

            // navigate to required screen
            if (token == null) startActivity(Intent(context, AuthActivity::class.java))
            else startActivity(Intent(context, NavigationActivity::class.java))
            finish()
        }
        Timer().schedule(navigate, 3000)
    }
}