package com.moose.foodies.features.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import com.moose.foodies.theme.FoodiesTheme

@ExperimentalAnimationApi
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Screen() }
    }

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun Screen(){
        FoodiesTheme {
            Surface(color = MaterialTheme.colors.primary) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val visible by remember { mutableStateOf(true) }
                    AnimatedVisibility(visible = visible, enter = fadeIn()) {
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
}