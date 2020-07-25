package com.moose.foodies.di.network

import com.moose.foodies.FoodiesApplication
import com.moose.foodies.util.PreferenceHelper
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

object Authenticator : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder().apply {
            headers(Headers.Builder().apply {
                addAll(originalRequest.headers)
                add(
                    "Authorization",
                    "Bearer ${PreferenceHelper.getAccessToken(FoodiesApplication.getInstance())!!}"
                )
                add("Content-Type", "application/json")
            }.build())
        }.build()
        return chain.proceed(newRequest)
    }
}