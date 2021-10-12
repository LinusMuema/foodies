package com.moose.foodies.remote

import com.moose.foodies.models.Auth
import com.moose.foodies.models.Credentials
import com.moose.foodies.models.Item
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiEndpoints {

    @POST("/api/auth/login")
    suspend fun login(@Body credentials: Credentials): Auth

    @POST("/api/auth/signup")
    suspend fun signup(@Body credentials: Credentials): Auth

    @GET("/api/auth/forgot/{email}")
    suspend fun forgot(@Path("email") email: String): ResponseBody

    @GET("/api/recipes/items")
    suspend fun getItems(@Query("update") update: String?): List<Item>
}