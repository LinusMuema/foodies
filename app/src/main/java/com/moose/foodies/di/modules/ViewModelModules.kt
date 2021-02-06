package com.moose.foodies.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.moose.foodies.di.ViewModelKey
import com.moose.foodies.di.factory.ViewModelFactory
import com.moose.foodies.features.favorites.FavoritesViewModel
import com.moose.foodies.features.fridge.FridgeViewModel
import com.moose.foodies.features.recipe.RecipeViewModel
import com.moose.foodies.features.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModules {

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel::class)
    abstract fun bindRecipeViewModel(recipeViewModel: RecipeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FridgeViewModel::class)
    abstract fun bindFridgeViewModel(fridgeViewModel: FridgeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}