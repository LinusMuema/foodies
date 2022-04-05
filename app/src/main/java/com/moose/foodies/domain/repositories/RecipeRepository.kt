package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.RecipesDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.Recipe
import com.moose.foodies.data.models.SearchData
import com.moose.foodies.util.Preferences
import javax.inject.Inject

interface RecipeRepository {
    suspend fun getItem(id: String): Item

    suspend fun getRecipe(id: String): Recipe

    suspend fun getFavorite(id: String):Recipe?

    suspend fun updateRecipe(recipe: Recipe)

    suspend fun searchRecipe(data: SearchData): List<Recipe>
}

class RecipeRepositoryImpl @Inject constructor(
    val recipesDao: RecipesDao,
    val preferences: Preferences,
    val recipesService: RecipesService,
): RecipeRepository {

    override suspend fun getItem(id: String): Item {
        return recipesDao.getItemById(id)
    }

    override suspend fun getRecipe(id: String): Recipe {
        return recipesDao.getRecipeById(id) ?: recipesService.getRecipe(id)
    }

    override suspend fun getFavorite(id: String): Recipe? {
        return recipesDao.getFavoriteById(id)
    }

    override suspend fun updateRecipe(recipe: Recipe) {
        return recipesDao.updateRecipe(recipe)
    }

    override suspend fun searchRecipe(data: SearchData): List<Recipe> {
        return recipesService.searchRecipe(data)
    }
}