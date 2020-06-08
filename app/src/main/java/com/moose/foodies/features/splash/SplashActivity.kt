package com.moose.foodies.features.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.moose.foodies.R
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.util.fadeIn
import com.moose.foodies.util.hideAllBars
import com.moose.foodies.util.slideUp
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity @Inject constructor() : AppCompatActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        this.hideAllBars()
        val widthDp = resources.displayMetrics.run { widthPixels / density }
        sharedPreferences.edit().putFloat("deviceWidth", widthDp).apply()

        if (sharedPreferences.getBoolean("logged", false)){
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
