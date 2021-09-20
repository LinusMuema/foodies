package com.moose.foodies.remote

import com.moose.foodies.util.Preferences
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

object Authenticator: Interceptor, BaseAuthenticator() {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.getToken()
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder().apply {
            val headers = Headers.Builder()
            headers.addAll(originalRequest.headers)
            headers.add("Authorization", "Bearer $token")
            headers(headers.build())
        }.build()
        return chain.proceed(newRequest)
    }
}

open class BaseAuthenticator {
    @Inject lateinit var preferences: Preferences
}