package com.moose.foodies.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.moose.foodies.models.Recipe
import com.moose.foodies.models.Recipes
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface Dao{
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): Flowable<List<Recipes>>

    @Query("SELECT * FROM favorites")
    fun getFavorites(): Observable<List<Recipe>>

    @Query("SELECT COUNT(*) FROM favorites")
    fun getFavoritesCount(): Single<Int>

    @Query("SELECT * FROM favorites WHERE id LIKE :id LIMIT 1")
    fun getOneFavorite(id: Int): Single<Recipe>

    @Insert(onConflict = REPLACE)
    fun insertRecipes(recipes: Recipes): Completable

    @Insert(onConflict = REPLACE)
    fun insertFavorite(recipe: Recipe): Completable

    @Insert(onConflict = REPLACE)
    fun insertFavorites(recipes: List<Recipe>): Completable

    @Query("DELETE FROM recipes")
    fun deleteRecipes(): Completable

    @Delete
    fun deleteFavorite(recipe: Recipe): Completable
}   