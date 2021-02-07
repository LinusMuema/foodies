package com.moose.foodies.local

import com.moose.foodies.features.feature_home.Recipe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class DbRepository @Inject constructor(private val dao: FoodiesDao)  {
    open fun getFavorites() = dao.getFavorites()

    open fun getFavoritesCount() = dao.getFavoritesCount()

    open fun getOneFavorite(id: Int) = dao.getFavoriteById(id)

    open fun insertFavorite(recipe: Recipe) = dao.insertFavorite(recipe)

    open fun deleteFavorite(id: Int) = dao.deleteFavorite(id)
}