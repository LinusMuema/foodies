package com.moose.foodies.features.splash

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import com.moose.foodies.R
import com.moose.foodies.features.BaseActivity
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.util.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.hideAllBars()
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val widthDp = resources.displayMetrics.run { widthPixels / density }
        PreferenceHelper.setDeviceWidth(this, widthDp)

        if (PreferenceHelper.getLoggedStatus(this)){
            Handler().postDelayed({
                this.pushWithoutHistory<HomeActivity>()
            }, 3000)
        }
        else{
            Handler().postDelayed({
                pushWithoutHistory<AuthActivity>()
            }, 3000)
        }
    }

    override fun onStart() {
        super.onStart()
        splash_image.fadeIn()
        splash_text.slideUp()
    }
}
