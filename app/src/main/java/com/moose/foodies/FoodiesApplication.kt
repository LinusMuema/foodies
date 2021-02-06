package com.moose.foodies

import android.app.Application
import com.moose.foodies.di.AppComponent
import com.moose.foodies.di.DaggerAppComponent
import com.moose.foodies.util.PreferenceHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class FoodiesApplication: Application(), HasAndroidInjector{

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        val widthDp = resources.displayMetrics.run { widthPixels / density }
        PreferenceHelper.setDeviceWidth(this, widthDp)

        instance = this
        appComponent = DaggerAppComponent.factory().create(this)
        appComponent.inject(this)
    }
    
    companion object{
        private lateinit var instance: FoodiesApplication

        @JvmStatic
        fun getInstance() = instance
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}