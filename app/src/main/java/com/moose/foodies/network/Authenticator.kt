package com.moose.foodies.network

import com.moose.foodies.FoodiesApplication
import com.moose.foodies.util.PreferenceHelper
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class Authenticator: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder().apply {
            headers(Headers.Builder().apply {
                addAll(originalRequest.headers)
                add("Authorization", PreferenceHelper.getAccessToken(FoodiesApplication.getInstance())!!)
                add("Content-Type", "application/json")
            }.build())
        }.build()
        return chain.proceed(newRequest)
    }
}