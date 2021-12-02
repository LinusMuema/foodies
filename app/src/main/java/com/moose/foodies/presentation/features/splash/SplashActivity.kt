package com.moose.foodies.presentation.features.splash

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.moose.foodies.R
import com.moose.foodies.presentation.components.CenterColumn
import com.moose.foodies.presentation.features.auth.AuthActivity
import com.moose.foodies.presentation.features.navigation.NavigationActivity
import com.moose.foodies.presentation.theme.FoodiesTheme
import com.moose.foodies.util.Preferences
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.timerTask

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@AndroidEntryPoint
@ExperimentalPagerApi
@ExperimentalAnimationApi
class SplashActivity : ComponentActivity() {

    @Inject lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
        redirect()
    }

    @Preview(name = "Light Theme")
    @Preview(name = "Dark Theme", uiMode = UI_MODE_NIGHT_YES)
    @Composable
    private fun Content(){
        FoodiesTheme {
            Surface(color = colors.background) {
                CenterColumn {
                    val animation = fadeIn(animationSpec = tween(2000))
                    val visible by remember { mutableStateOf(true) }
                    AnimatedVisibility(visible = visible, enter = animation) {
                        Icon(
                            modifier = Modifier.size(150.dp),
                            contentDescription = "splash icon",
                            tint = colors.secondary,
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