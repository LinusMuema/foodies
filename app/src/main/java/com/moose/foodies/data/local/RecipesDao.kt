package com.moose.foodies.data.local

import androidx.room.*
import com.moose.foodies.domain.models.Item
import com.moose.foodies.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItems(items: List<Item>)

    @Query("select * from item where type = 'Ingredients'")
    fun getIngredients(): Flow<List<Item>>

    @Query("select * from item where name like :name and type = :type limit 5")
    suspend fun searchItem(name: String, type: String): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRecipe(vararg recipe: Recipe)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecipe(vararg recipe: Recipe)

    @Query("select * from recipe where type = 'PERSONAL'")
    fun getUserRecipes(): Flow<List<Recipe>>

    @Query("select * from recipe order by random() limit 10")
    suspend fun getRandomRecipes(): List<Recipe>

    @Query("select * from recipe where _id = :id limit 1")
    suspend fun getRecipeById(id: String): Recipe?

    @Query("select * from recipe where _id = :id and type = 'FAVORITE' limit 1")
    suspend fun getFavoriteById(id: String): Recipe?

    @Query("select * from item where _id = :id limit 1")
    suspend fun getItemById(id: String): Item

    @Query("delete from recipe where type != 'PERSONAL' and type != 'FAVORITE'")
    suspend fun nukeFeedRecipes()

    @Query("delete from recipe")
    suspend fun nukeRecipes()

    @Transaction
    suspend fun updateFeedRecipes(recipes: List<Recipe>){
        nukeFeedRecipes()
        addRecipe(*recipes.toTypedArray())
    }
}