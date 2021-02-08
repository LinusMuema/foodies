package com.moose.foodies.remote

import com.moose.foodies.features.feature_home.domain.HomeData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class ApiRepository @Inject constructor(private val apiEndpoints: ApiEndpoints) {

    open fun backupFavorites(recipes: HomeData) = apiEndpoints.backupRecipes(recipes)

}