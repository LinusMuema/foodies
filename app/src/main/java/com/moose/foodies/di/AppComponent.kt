package com.moose.foodies.di

import android.content.Context
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.db.DatabaseModules
import com.moose.foodies.di.modules.ActivitiesModule
import com.moose.foodies.di.modules.FragmentsModule
import com.moose.foodies.di.modules.ViewModelModules
import com.moose.foodies.di.modules.WorkerModules
import com.moose.foodies.features.auth.AuthModule
import com.moose.foodies.network.ApiModules
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AuthModule::class,
    ActivitiesModule::class,
    FragmentsModule::class,
    DatabaseModules::class,
    ViewModelModules::class,
    WorkerModules::class,
    ApiModules::class])
interface AppComponent{

    fun inject(app: FoodiesApplication)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}