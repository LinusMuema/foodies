package com.moose.foodies.remote

import okhttp3.Interceptor
import okhttp3.Response

object CacheInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        return response.newBuilder()
            .header("Cache-Control", "public, only-if-cached, max-stale=86400, max-age=86400")
            .build()
    }
}