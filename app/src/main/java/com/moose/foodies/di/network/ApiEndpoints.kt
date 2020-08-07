package com.moose.foodies.di.network

import com.moose.foodies.models.*
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiEndpoints {
    @POST("/api/auth/login")
    fun login(@Body credentials: Credentials): Single<AuthResponse>

    @GET("/api/auth/{email}/reset")
    fun reset(@Path("email") email: String): Single<AuthResponse>

    @GET("/api/intolerances")
    fun getAllIntolerances(): Single<Intolerances>

    @POST("/api/intolerances")
    fun updateIntolerances(@Body intolerances: Intolerances): Single<IntolerancesUpdate>

    @GET("/api/recipes/random")
    fun getRecipes(): Single<Recipes>

    @GET("/api/recipes/search/{name}")
    fun searchRecipe(@Path("name") name: String): Single<RecipeSearch>

    @GET("/api/recipes/search/ingredients/{ingredients}")
    fun searchFridgeRecipes(@Path("ingredients") ingredients: String): Single<FridgeSearch>

    @GET("/api/recipes/one/{id}")
    fun getRecipeById(@Path("id") id: String): Single<FridgeRecipe>

    @POST("/api/recipes/favorites")
    fun backupRecipes(@Body favorites: Recipes): Single<AuthResponse>

    @GET("/api/recipes/favorites")
    fun getBackedUpRecipes(): Single<List<Recipe>>
}