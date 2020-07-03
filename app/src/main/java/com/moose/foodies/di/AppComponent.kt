package com.moose.foodies.di

import android.content.Context
import com.moose.foodies.di.modules.*
import com.moose.foodies.features.auth.AuthActivity
import com.moose.foodies.features.favorites.FavoritesActivity
import com.moose.foodies.features.home.HomeActivity
import com.moose.foodies.features.intolerances.IntolerancesActivity
import com.moose.foodies.features.recipe.RecipeActivity
import com.moose.foodies.features.reset.ResetPasswordActivity
import com.moose.foodies.features.search.RecipesFragment
import com.moose.foodies.features.search.SearchActivity
import com.moose.foodies.features.search.VideosFragment
import com.moose.foodies.features.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DatabaseModules::class,
    ViewModelModules::class,
    SharedPreferenceModules::class,
    ApiModules::class])

interface AppComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(splashActivity: SplashActivity)

    fun inject(authActivity: AuthActivity)

    fun inject(resetPasswordActivity: ResetPasswordActivity)

    fun inject(intolerancesActivity: IntolerancesActivity)

    fun inject(homeActivity: HomeActivity)

    fun inject(recipeActivity: RecipeActivity)

    fun inject(favoritesActivity: FavoritesActivity)

    fun inject(searchActivity: SearchActivity)

    fun inject(videosFragment: VideosFragment)

    fun inject(recipesFragment: RecipesFragment)
}