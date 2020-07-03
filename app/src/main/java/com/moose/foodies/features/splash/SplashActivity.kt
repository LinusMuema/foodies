package com.moose.foodies.features.splash

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.R
import com.moose.foodies.di.DaggerAppComponent
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.util.PreferenceHelper
import com.moose.foodies.util.fadeIn
import com.moose.foodies.util.hideAllBars
import com.moose.foodies.util.slideUp
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity @Inject constructor() : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerAppComponent.factory().create(this).inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.hideAllBars()
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val widthDp = resources.displayMetrics.run { widthPixels / density }
        PreferenceHelper.setDeviceWidth(this, widthDp)

        if (PreferenceHelper.getLoggedStatus(this)){
            Handler().postDelayed({
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }, 3000)
        }
        else{
            Handler().postDelayed({
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }, 3000)
        }
    }

    override fun onStart() {
        super.onStart()
        splash_image.fadeIn()
        splash_text.slideUp()
    }
}
