package com.moose.foodies.remote

import com.moose.foodies.features.feature_auth.domain.Credential
import com.moose.foodies.features.feature_auth.domain.TokenResponse
import com.moose.foodies.features.feature_favorites.domain.Backup
import com.moose.foodies.features.feature_favorites.domain.BackupStatus
import com.moose.foodies.features.feature_home.domain.HomeData
import com.moose.foodies.features.feature_home.domain.Recipe
import com.moose.foodies.features.feature_search.domain.SearchResults
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import com.moose.foodies.features.feature_ingredients.domain.Recipe as IngredientsSearchRecipe

interface ApiEndpoints {
    @POST("/api/auth/register")
    fun register(@Body credential: Credential): Single<TokenResponse>

    @GET("/api/recipes")
    fun getRecipes(): Single<HomeData>

    @GET("/api/recipes/search/{query}")
    fun searchRecipes(@Path("query") name: String): Single<SearchResults>

    @GET("/api/recipes/{id}")
    fun getRecipeById(@Path("id") id: Int): Single<Recipe>

    @POST("/api/recipes/favorites/backup")
    fun backupRecipes(@Body backup: Backup): Single<BackupStatus>

    @GET("/api/recipes/favorites/backup")
    fun getBackedUpRecipes(): Single<List<Recipe>>

    @GET("/api/recipes/ingredients/{ingredients}")
    fun getRecipesByIngredients(@Path("ingredients") ingredients: String): Single<List<IngredientsSearchRecipe>>
}