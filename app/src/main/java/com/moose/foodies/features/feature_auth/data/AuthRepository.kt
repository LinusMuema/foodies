package com.moose.foodies.features.feature_auth.data

import com.moose.foodies.features.feature_auth.domain.Credential
import com.moose.foodies.features.feature_auth.domain.TokenResponse
import com.moose.foodies.features.feature_home.domain.Recipe
import com.moose.foodies.local.FoodiesDao
import com.moose.foodies.remote.ApiEndpoints
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface AuthRepository {
    fun register(email: Credential): Single<TokenResponse>

    fun getBackedUpFavorites(): Single<List<Recipe>>

    fun updateFavorites(recipes: List<Recipe>): Completable
}

class AuthRepositoryImpl @Inject constructor(private val dao: FoodiesDao, private val api: ApiEndpoints):
    AuthRepository {

    override fun register(email: Credential): Single<TokenResponse> = api.register(email)

    override fun updateFavorites(recipes: List<Recipe>): Completable = dao.insertFavorites(recipes)

    override fun getBackedUpFavorites(): Single<List<Recipe>> = api.getBackedUpRecipes()
}