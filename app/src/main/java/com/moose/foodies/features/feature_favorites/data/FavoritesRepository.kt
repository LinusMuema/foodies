package com.moose.foodies.features.feature_favorites.data

import com.moose.foodies.features.feature_home.domain.Recipe
import com.moose.foodies.local.FoodiesDao
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

interface FavoritesRepository {
    fun getFavorites(): Flowable<List<Recipe>>

    fun deleteFavorite(id: Int): Completable
}

class FavoritesRepositoryImpl @Inject constructor(private val dao: FoodiesDao): FavoritesRepository {

    override fun getFavorites(): Flowable<List<Recipe>> = dao.getFavorites()

    override fun deleteFavorite(id: Int): Completable = dao.deleteFavorite(id)

}