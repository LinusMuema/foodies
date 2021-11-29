package com.moose.foodies.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moose.foodies.models.Item
import com.moose.foodies.models.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items: List<Item>)

    @Query("select * from item where name like :name and type = :type limit 5")
    suspend fun searchItem(name: String, type: String): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(vararg recipe: Recipe)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecipe(vararg recipe: Recipe)

    @Query("select * from recipe where type = 'PERSONAL'")
    fun getUserRecipes(): Flow<List<Recipe>>

    @Query("select * from recipe where _id = :id limit 1")
    suspend fun getRecipeById(id: String): Recipe

    @Query("select * from recipe where _id = :id and type = 'FAVORITE' limit 1")
    suspend fun getFavoriteById(id: String): Recipe?

    @Query("select * from item where _id = :id limit 1")
    suspend fun getItemById(id: String): Item

    @Query("delete from recipe")
    fun nukeRecipes()
}