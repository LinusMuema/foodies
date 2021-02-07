package com.moose.foodies.di

import android.content.Context
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.db.DatabaseModules
import com.moose.foodies.di.factory.ViewModelFactoryModule
import com.moose.foodies.features.auth.AuthModule
import com.moose.foodies.features.favorites.FavoritesModule
import com.moose.foodies.features.home.HomeModule
import com.moose.foodies.features.ingredients.IngredientsModule
import com.moose.foodies.features.recipe.RecipeModule
import com.moose.foodies.features.search.SearchModule
import com.moose.foodies.network.ApiModules
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApiModules::class,
    AuthModule::class,
    HomeModule::class,
    RecipeModule::class,
    SearchModule::class,
    FavoritesModule::class,
    DatabaseModules::class,
    IngredientsModule::class,
    ViewModelFactoryModule::class,
    AndroidInjectionModule::class,
])
interface AppComponent{

    fun inject(app: FoodiesApplication)

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }
}