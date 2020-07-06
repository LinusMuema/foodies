package com.moose.foodies.di

import com.moose.foodies.FoodiesApplication
import com.moose.foodies.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ActivitiesModule::class,
    FragmentsModule::class,
    DatabaseModules::class,
    ViewModelModules::class,
    ApiModules::class])

interface AppComponent {

    fun inject(app: FoodiesApplication)

    @Component.Builder
    interface Builder{

        fun create(): AppComponent

        @BindsInstance
        fun applicationBind(app: FoodiesApplication): Builder
    }

}