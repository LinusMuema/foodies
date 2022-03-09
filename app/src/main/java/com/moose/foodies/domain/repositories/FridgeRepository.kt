package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.RecipesDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.domain.models.IngredientsSearch
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FridgeRepository {
    val ingredients: Flow<List<Item>>

    suspend fun getSuggestions(items: List<Item>): List<Recipe>
}

class FridgeRepositoryImpl @Inject constructor(val recipesDao: RecipesDao, val api: RecipesService): FridgeRepository {
    override val ingredients: Flow<List<Item>>
        get() = recipesDao.getIngredients()

    override suspend fun getSuggestions(items: List<Item>): List<Recipe> {
        val search = IngredientsSearch(ingredients = items.map { it._id })
        return api.getSuggestions(search)
    }

}