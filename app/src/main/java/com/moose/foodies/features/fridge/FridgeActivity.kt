package com.moose.foodies.features.fridge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moose.foodies.R
import com.moose.foodies.util.ActivityHelper
import dagger.android.AndroidInjection

class FridgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        ActivityHelper.initialize(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fridge)
    }
}