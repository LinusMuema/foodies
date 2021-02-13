package com.moose.foodies.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.moose.foodies.FoodiesApplication
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.File
import javax.inject.Singleton

@Module
@ExperimentalSerializationApi
class ApiModules {

    private val baseUrl = "http://foodies.moose.ac/"
    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val converter = Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType())

    @Singleton
    @Provides
    fun provideRxAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        // Setup the cache
        val cacheDir = File(FoodiesApplication.getInstance().cacheDir, "responses")
        val cacheSize = 5 * 1024 * 1024
        val cache = Cache(cacheDir, cacheSize.toLong())

        return OkHttpClient.Builder()
            .cache(cache)
            .addNetworkInterceptor(CacheInterceptor)
            .addInterceptor(Authenticator)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, adapter: RxJava2CallAdapterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl).client(client)
            .addCallAdapterFactory(adapter)
            .addConverterFactory(converter)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): ApiEndpoints {
        return retrofit.create(ApiEndpoints::class.java)
    }
}