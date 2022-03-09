package com.moose.foodies.domain.repositories

import com.moose.foodies.data.local.RecipesDao
import com.moose.foodies.data.remote.RecipesService
import com.moose.foodies.domain.models.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FridgeRepository {
    val ingredients: Flow<List<Item>>
}

class FridgeRepositoryImpl @Inject constructor(val recipesDao: RecipesDao, val api: RecipesService): FridgeRepository {
    override val ingredients: Flow<List<Item>>
        get() = recipesDao.getItems()

}