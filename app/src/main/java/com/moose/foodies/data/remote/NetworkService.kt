package com.moose.foodies.data.remote

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.moose.foodies.util.ApiError
import com.moose.foodies.util.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.lang.Exception
import javax.inject.Singleton
import kotlinx.serialization.json.Json as json


@EntryPoint
@InstallIn(SingletonComponent::class)
interface ClientHelper {
    fun client(): HttpClient
}

@Module
@ExperimentalSerializationApi
@InstallIn(SingletonComponent::class)
object NetworkService {

    private const val baseUrl = "http://foodies.moose.ac/"

    private val level = HttpLoggingInterceptor.Level.BODY
    private val interceptor = HttpLoggingInterceptor().setLevel(level)

    private val serializer = json { ignoreUnknownKeys = true }
    private val converter = serializer.asConverterFactory("application/json".toMediaType())


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

    @Provides
    @Singleton
    fun provideClient(preferences: Preferences): HttpClient {
        val token = preferences.getToken()
        return HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.DEFAULT
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer(json {
                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }
            install(Auth){
                bearer {
                    loadTokens {
                        BearerTokens(accessToken = token?: "", refreshToken = "")
                    }
                }
            }
            defaultRequest {
                if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
            HttpResponseValidator {
                handleResponseException {
                    if (it !is ClientRequestException) return@handleResponseException
                    val error: ApiError = json.decodeFromString(it.response.readText())
                    throw Exception(error.message)
                }
            }
        }
    }
}