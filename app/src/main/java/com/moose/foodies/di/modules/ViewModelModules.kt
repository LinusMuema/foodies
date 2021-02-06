package com.moose.foodies.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.di.ViewModelKey
import com.moose.foodies.di.factory.ViewModelFactory
import com.moose.foodies.features.favorites.FavoritesViewModel
import com.moose.foodies.features.fridge.FridgeViewModel
import com.moose.foodies.features.home.HomeViewModel
import com.moose.foodies.features.intolerances.IntolerancesViewModel
import com.moose.foodies.features.recipe.RecipeViewModel
import com.moose.foodies.features.reset.ResetPasswordViewModel
import com.moose.foodies.features.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModules {

    @IntoMap
    @Binds
    @ViewModelKey(IntolerancesViewModel::class)
    abstract fun bindIntroViewModel(introlerancesViewModel: IntolerancesViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(ResetPasswordViewModel::class)
    abstract fun bindResetPasswordViewModel(resetPasswordViewModel: ResetPasswordViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(RecipeViewModel::class)
    abstract fun bindRecipeViewModel(recipeViewModel: RecipeViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(FridgeViewModel::class)
    abstract fun bindFridgeViewModel(fridgeViewModel: FridgeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}