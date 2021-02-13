package com.moose.foodies.features.feature_favorites.data

import com.moose.foodies.features.feature_favorites.domain.Backup
import com.moose.foodies.features.feature_favorites.domain.BackupStatus
import com.moose.foodies.features.feature_home.domain.Recipe
import com.moose.foodies.local.FoodiesDao
import com.moose.foodies.remote.ApiEndpoints
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface FavoritesRepository {
    fun getFavorites(): Single<List<Recipe>>

    fun deleteFavorite(id: Int): Completable

    fun backupFavorites(recipes: Backup): Single<BackupStatus>
}

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FoodiesDao,
    private val api: ApiEndpoints
): FavoritesRepository {

    override fun getFavorites(): Single<List<Recipe>> = dao.getFavorites()

    override fun deleteFavorite(id: Int): Completable = dao.deleteFavorite(id)

    override fun backupFavorites(recipes: Backup): Single<BackupStatus> = api.backupRecipes(recipes)
}