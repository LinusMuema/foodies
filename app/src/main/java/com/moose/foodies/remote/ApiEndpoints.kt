package com.moose.foodies.remote

import com.moose.foodies.models.*
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiEndpoints {

    // Authentication endpoints
    @POST("/api/auth/login")
    suspend fun login(@Body credentials: Credentials): Auth

    @POST("/api/auth/signup")
    suspend fun signup(@Body credentials: Credentials): Auth

    @GET("/api/auth/forgot/{email}")
    suspend fun forgot(@Path("email") email: String): ResponseBody


    // Recipes endpoints
    @POST("/api/recipes")
    suspend fun uploadRecipe(@Body recipe: RawRecipe): Recipe

    @GET("/api/recipes/user/{id}")
    suspend fun getUserRecipes(@Path("id") id: String): List<Recipe>

    @GET("/api/recipes/items")
    suspend fun getItems(@Query("update") update: String?): List<Item>

    // User's endpoints
    @PUT("/api/users/update")
    suspend fun updateProfile(@Body update: Profile): Profile
}