package com.moose.foodies.features.feature_recipe

import androidx.lifecycle.ViewModel
import com.moose.foodies.di.ActivityScope
import com.moose.foodies.di.ViewModelKey
import com.moose.foodies.features.feature_recipe.data.RecipeRepository
import com.moose.foodies.features.feature_recipe.data.RecipeRepositoryImpl
import com.moose.foodies.features.feature_recipe.presentation.RecipeActivity
import com.moose.foodies.features.feature_recipe.presentation.RecipeViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class RecipeModule {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun provideRecipe(): RecipeActivity

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel::class)
    abstract fun bindRecipeViewModel(recipeViewModel: RecipeViewModel): ViewModel

    @Binds
    abstract fun provideRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository
}