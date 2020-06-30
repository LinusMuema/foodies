package com.moose.foodies.network

import com.moose.foodies.BuildConfig
import com.moose.foodies.models.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiEndpoints {
    @POST("/api/auth/login")
    fun login(@Body credentials: Credentials): Observable<AuthResponse>

    @GET("/api/auth/{email}/reset")
    fun reset(@Path("email") email: String): Observable<AuthResponse>

    @GET("/api/intolerances")
    fun getAllIntolerances(): Observable<Intolerances>

    @POST("/api/intolerances")
    fun updateIntolerances(@Body intolerances: Intolerances): Observable<IntolerancesUpdate>

    @GET("/api/recipes/random")
    fun getRecipes(): Observable<Recipes>

    @GET("/api/recipes/search/{name}")
    fun searchRecipe(@Path("name") name: String): Observable<RecipeSearch>

    @GET("/api/recipes/search/ingredients/{ingredients}")
    fun searchRecipeByIngredients(@Path("ingredients") ingredients: String): Observable<FridgeSearch>

}