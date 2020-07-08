package com.moose.foodies.di

import android.content.Context
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivitiesModule::class,
    FragmentsModule::class,
    DatabaseModules::class,
    ViewModelModules::class,
    ApiModules::class])

interface AppComponent{

    fun inject(app: FoodiesApplication)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}