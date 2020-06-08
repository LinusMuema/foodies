package com.moose.foodies.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.di.VMKey
import com.moose.foodies.di.factory.ViewModelFactory
import com.moose.foodies.features.home.HomeViewModel
import com.moose.foodies.features.intolerances.IntolerancesViewModel
import com.moose.foodies.features.auth.AuthViewModel
import com.moose.foodies.features.favorites.FavoritesViewModel
import com.moose.foodies.features.recipe.RecipeViewModel
import com.moose.foodies.features.reset.ResetPasswordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModules {

    @IntoMap
    @Binds
    @VMKey(IntolerancesViewModel::class)
    abstract fun bindIntroViewModel(introlerancesViewModel: IntolerancesViewModel): ViewModel

    @IntoMap
    @Binds
    @VMKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(authViewModel: AuthViewModel): ViewModel

    @IntoMap
    @Binds
    @VMKey(ResetPasswordViewModel::class)
    abstract fun bindResetPasswordViewModel(resetPasswordViewModel: ResetPasswordViewModel): ViewModel

    @IntoMap
    @Binds
    @VMKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @IntoMap
    @Binds
    @VMKey(RecipeViewModel::class)
    abstract fun bindRecipeViewModel(recipeViewModel: RecipeViewModel): ViewModel

    @IntoMap
    @Binds
    @VMKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}