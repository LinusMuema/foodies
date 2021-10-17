package com.moose.foodies.features.add

import androidx.lifecycle.LiveData
import com.moose.foodies.local.ItemsDao
import com.moose.foodies.models.Item
import javax.inject.Inject


class AddRepositoryImpl @Inject constructor(private val dao: ItemsDao): AddRepository {

    override suspend fun getItems(name: String, type: String): List<Item> {
        return dao.searchItem("%$name%", type)
    }
}