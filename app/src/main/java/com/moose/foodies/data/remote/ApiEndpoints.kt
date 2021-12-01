package com.moose.foodies.data.remote

import com.moose.foodies.domain.models.*
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
    @GET("/api/recipes")
    suspend fun getFeed(): List<Recipe>

    @POST("/api/recipes")
    suspend fun uploadRecipe(@Body recipe: RawRecipe): Recipe

    @GET("/api/recipes/user/{id}")
    suspend fun getUserRecipes(@Path("id") id: String): List<Recipe>

    @GET("/api/recipes/items")
    suspend fun getItems(@Query("update") update: String?): List<Item>

    // User's endpoints
    @GET("/api/users/discover")
    suspend fun getChefs(): List<Profile>

    @PUT("/api/users/update")
    suspend fun updateProfile(@Body update: Profile): Profile
}