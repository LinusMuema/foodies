package com.moose.foodies.network

import android.util.Log
import com.moose.foodies.FoodiesApplication
import com.moose.foodies.util.PreferenceHelper
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class Authenticator: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder().apply {
            headers(Headers.Builder().apply {
                addAll(originalRequest.headers)
                val token = PreferenceHelper.getAccessToken(FoodiesApplication.getInstance())
                if (token != null) {
                    add("Authorization", token)
                    Log.e("AuthorizationToken :: ", token)
                }
                add("Content-Type", "application/json")
            }.build())
        }.build()
        return chain.proceed(newRequest)
    }
}