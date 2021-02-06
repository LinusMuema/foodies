package com.moose.foodies.db

import com.moose.foodies.models.Recipe
import com.moose.foodies.models.Recipes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DbRepository @Inject constructor(private val dao: FoodiesDao)  {
    open fun getTodaysRecipes() = dao.getAllRecipes()

    open fun getFavorites() = dao.getFavorites()

    open fun getFavoritesCount() = dao.getFavoritesCount()

    open fun getOneFavorite(id: Int) = dao.getOneFavorite(id)

    open fun insertTodaysRecipes(recipes: Recipes) = dao.insertRecipes(recipes)

    open fun insertFavorite(recipe: Recipe) = dao.insertFavorite(recipe)

    open fun insertFavorites(recipes: List<Recipe>) = dao.insertFavorites(recipes)

    open fun deleteRecipes() = dao.deleteRecipes()

    open fun deleteFavorite(recipe: Recipe) = dao.deleteFavorite(recipe)
}