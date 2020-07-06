package com.moose.foodies

import android.app.Activity
import android.app.Application
import com.moose.foodies.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

open class FoodiesApplication: Application(), HasActivityInjector{

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this
        DaggerAppComponent.builder().applicationBind(this).create().inject(this)
    }

    companion object{
        private lateinit var instance: FoodiesApplication

        @JvmStatic
        fun getInstance() = instance
    }

    override fun activityInjector(): AndroidInjector<Activity> = androidInjector
}