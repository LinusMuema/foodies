package com.moose.foodies.presentation.features.auth

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.moose.foodies.R
import com.moose.foodies.presentation.components.*
import com.moose.foodies.presentation.features.navigation.NavigationActivity
import com.moose.foodies.presentation.theme.FoodiesTheme
import com.moose.foodies.util.onError
import com.moose.foodies.util.onSuccess
import com.moose.foodies.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class AuthActivity : ComponentActivity() {
    private val viewmodel: AuthViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Content() }
    }

    @Composable
    private fun Content(){
        val screen by remember { viewmodel.screen }
        val result by remember { viewmodel.result }

        result?.let {
            viewmodel.changeLoading(false)

            it.onError { error -> toast(error) }
            it.onSuccess {
                startActivity(Intent(this, NavigationActivity::class.java))
                finish()
            }
        }

        FoodiesTheme {
            Surface(color = colors.background) {
                CenterColumn {
                    Icon(
                        modifier = Modifier.size(150.dp),
                        contentDescription = "splash icon",
                        tint = colors.secondary,
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
}