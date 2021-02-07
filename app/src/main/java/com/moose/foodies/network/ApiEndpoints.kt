package com.moose.foodies.network

import com.moose.foodies.features.auth.Credential
import com.moose.foodies.features.auth.TokenResponse
import com.moose.foodies.features.home.HomeData
import com.moose.foodies.models.*
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiEndpoints {
    @POST("/api/auth/register")
    fun register(@Body credential: Credential): Single<TokenResponse>

    @GET("/api/intolerances")
    fun getAllIntolerances(): Single<Intolerances>

    @POST("/api/intolerances")
    fun updateIntolerances(@Body intolerances: Intolerances): Single<IntolerancesUpdate>

    @GET("/api/recipes")
    fun getRecipes(): Single<HomeData>

    @GET("/api/recipes/search/{name}")
    fun searchRecipe(@Path("name") name: String): Single<RecipeSearch>

    @GET("/api/recipes/search/ingredients/{ingredients}")
    fun searchFridgeRecipes(@Path("ingredients") ingredients: String): Single<FridgeSearch>

    @GET("/api/recipes/one/{id}")
    fun getRecipeById(@Path("id") id: String): Single<FridgeRecipe>

    @POST("/api/recipes/favorites")
    fun backupRecipes(@Body favorites: HomeData): Observable<AuthResponse>

    @GET("/api/recipes/favorites")
    fun getBackedUpRecipes(): Single<List<Recipe>>
}