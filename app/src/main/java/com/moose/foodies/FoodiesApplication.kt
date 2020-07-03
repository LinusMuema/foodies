package com.moose.foodies

import android.app.Application
import com.moose.foodies.di.AppComponent
import com.moose.foodies.di.DaggerAppComponent

open class FoodiesApplication: Application(){

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.factory().create(applicationContext)
    }

    companion object{
        private lateinit var instance: FoodiesApplication

        @JvmStatic
        fun getInstance() = instance
    }
}