package com.moose.foodies.features.feature_splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.moose.foodies.databinding.ActivitySplashBinding
import com.moose.foodies.features.feature_auth.presentation.AuthActivity
import com.moose.foodies.features.feature_home.presentation.HomeActivity
import com.moose.foodies.util.PreferenceHelper
import com.moose.foodies.util.extensions.fadeIn
import com.moose.foodies.util.extensions.hideAllBars
import com.moose.foodies.util.extensions.pop
import com.moose.foodies.util.extensions.slideUp

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.hideAllBars()

        binding = ActivitySplashBinding.inflate(layoutInflater)

        val loggedIn = PreferenceHelper.getLoggedStatus(this)
        if (loggedIn) navigate { pop<HomeActivity>() }
        else navigate { pop<AuthActivity>() }

        setContentView(binding.root)
    }

    private fun navigate(destination: () -> Unit){
        Handler(Looper.getMainLooper()).postDelayed({
            destination()
        }, 3000)
    }

    override fun onStart() {
        super.onStart()

        with(binding){
            splashImage.fadeIn()
            splashText.slideUp()
        }
    }
}
