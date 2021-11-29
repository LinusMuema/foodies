package com.moose.foodies.features.recipe

import com.moose.foodies.models.Item
import com.moose.foodies.models.Recipe
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getItem(id: String): Item

    suspend fun getRecipe(id: String): Recipe

    suspend fun getFavorite(id: String):Recipe?

    suspend fun updateRecipe(recipe: Recipe)
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RecipeRepositoryBinding {

    @Binds
    abstract fun provideRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository
}