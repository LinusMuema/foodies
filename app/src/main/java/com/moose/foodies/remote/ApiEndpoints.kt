package com.moose.foodies.remote

import com.moose.foodies.models.Auth
import com.moose.foodies.models.Credentials
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiEndpoints {

    @POST("/api/auth/login")
    suspend fun login(@Body credentials: Credentials): Auth

    @POST("/api/auth/signup")
    suspend fun signup(@Body credentials: Credentials): Auth

    @GET("/api/auth/forgot/{email}")
    suspend fun forgot(@Path("email") email: String): ResponseBody
}