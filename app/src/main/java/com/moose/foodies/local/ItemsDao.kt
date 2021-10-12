package com.moose.foodies.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moose.foodies.models.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items: List<Item>)

    @Query("select * from item where name like :name and type = :type")
    fun searchItem(name: String, type: String): Flow<List<Item>>
}