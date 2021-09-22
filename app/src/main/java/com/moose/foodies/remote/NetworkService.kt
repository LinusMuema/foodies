package com.moose.foodies.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@ExperimentalSerializationApi
@InstallIn(SingletonComponent::class)
object NetworkService {

    private const val baseUrl = "http://foodies.moose.ac/"

    private val level = HttpLoggingInterceptor.Level.BODY
    private val interceptor = HttpLoggingInterceptor().setLevel(level)

    private val json = Json { ignoreUnknownKeys = true }
    private val converter = json.asConverterFactory("application/json".toMediaType())


    private val client = OkHttpClient.Builder()
        .addInterceptor(Authenticator)
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(converter)
        .baseUrl(baseUrl)
        .client(client)
        .build()

    @Provides
    @Singleton
    fun provideApi(): ApiEndpoints {
        return retrofit.create(ApiEndpoints::class.java)
    }
}