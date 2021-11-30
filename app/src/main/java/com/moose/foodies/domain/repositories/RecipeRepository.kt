package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.ItemsDao
import com.moose.foodies.data.remote.ApiEndpoints
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.Recipe
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

interface RecipeRepository {
    suspend fun getItem(id: String): Item

    suspend fun getRecipe(id: String): Recipe

    suspend fun getFavorite(id: String):Recipe?

    suspend fun updateRecipe(recipe: Recipe)
}

class RecipeRepositoryImpl @Inject constructor(val api: ApiEndpoints, val itemsDao: ItemsDao): RecipeRepository {
    override suspend fun getItem(id: String): Item {
        return itemsDao.getItemById(id)
    }

    override suspend fun getRecipe(id: String): Recipe {
        return itemsDao.getRecipeById(id)
    }

    override suspend fun getFavorite(id: String): Recipe? {
        return itemsDao.getFavoriteById(id)
    }

    override suspend fun updateRecipe(recipe: Recipe) {
        return itemsDao.updateRecipe(recipe)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RecipeRepositoryBinding {

    @Binds
    abstract fun provideRecipeRepository(impl: RecipeRepositoryImpl): RecipeRepository
}