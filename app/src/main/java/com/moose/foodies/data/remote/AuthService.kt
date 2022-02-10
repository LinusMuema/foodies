package com.moose.foodies.data.remote

import com.moose.foodies.FoodiesApplication
import com.moose.foodies.domain.models.Auth
import com.moose.foodies.domain.models.Credentials
import com.moose.foodies.util.BASE_URL
import dagger.hilt.android.EntryPointAccessors
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class AuthService @Inject constructor(private val client: HttpClient) {

    suspend fun login(credentials: Credentials): Auth {
        val url = "$BASE_URL/api/auth/login"
        return client.post { url(url); body = credentials }
    }

    suspend fun signup(credentials: Credentials): Auth{
        val url = "$BASE_URL/api/auth/signup"
        return client.post{ url(url); body = credentials }
    }

    suspend fun forgot(email: String): Any {
        val url = "$BASE_URL/api/auth/forgot/$email"
        return client.get(url)
    }
}