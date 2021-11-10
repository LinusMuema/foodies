package com.moose.foodies.features.recipe

import com.moose.foodies.local.ItemsDao
import com.moose.foodies.models.Item
import com.moose.foodies.models.Recipe
import com.moose.foodies.remote.ApiEndpoints
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: ApiEndpoints,
    private val itemsDao: ItemsDao
): RecipeRepository {

    override suspend fun getItem(id: String): Item {
        return itemsDao.getItemById(id)
    }

    override suspend fun getRecipe(id: String): Recipe {
        return itemsDao.getRecipeById(id)
    }
}