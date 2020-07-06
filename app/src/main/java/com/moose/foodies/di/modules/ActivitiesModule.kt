package com.moose.foodies.di.modules

import com.moose.foodies.di.ActivityScope
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.features.favorites.FavoritesActivity
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.features.intolerances.IntolerancesActivity
import com.moose.foodies.features.recipe.RecipeActivity
import com.moose.foodies.features.reset.ResetPasswordActivity
import com.moose.foodies.features.search.SearchActivity
import com.moose.foodies.features.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideSplash(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideAuth(): AuthActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideReset(): ResetPasswordActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideIntolerances(): IntolerancesActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideHome(): HomeActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideRecipe(): RecipeActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideFavorites(): FavoritesActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideSearch(): SearchActivity
}