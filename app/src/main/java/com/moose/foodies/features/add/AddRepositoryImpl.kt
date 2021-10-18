package com.moose.foodies.features.add

import androidx.lifecycle.LiveData
import com.moose.foodies.local.ItemsDao
import com.moose.foodies.models.Item
import com.moose.foodies.models.RawRecipe
import com.moose.foodies.models.Recipe
import com.moose.foodies.remote.ApiEndpoints
import javax.inject.Inject

class AddRepositoryImpl @Inject constructor(private val dao: ItemsDao, private val api: ApiEndpoints): AddRepository {

    override suspend fun getItems(name: String, type: String): List<Item> {
        return dao.searchItem("%$name%", type)
    }

    override suspend fun uploadRecipe(recipe: RawRecipe): Recipe {
        val result = api.uploadRecipe(recipe)
        dao.addRecipe(result)
        return result
    }
}