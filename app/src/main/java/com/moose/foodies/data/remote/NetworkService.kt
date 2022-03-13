package com.moose.foodies.data.remote

import android.util.Log
import com.moose.foodies.util.ApiError
import com.moose.foodies.util.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.auth.*
import io.ktor.client.features.auth.providers.*
import io.ktor.client.features.cache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import java.net.UnknownHostException
import kotlinx.serialization.json.Json as json

@Module
@ExperimentalSerializationApi
@InstallIn(SingletonComponent::class)
object NetworkService {

    @Provides
    fun provideClient(preferences: Preferences): HttpClient {
        return HttpClient(Android) {
            // logging settings
            install(Logging) {
                level = LogLevel.ALL
                logger = FoodiesLogger
            }

            // cache mechanism
            install(HttpCache)

            // serialization using kotlinx.serialization
            install(JsonFeature) {
                serializer = KotlinxSerializer(json {
                    isLenient = true
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }

            // Bearer tokens settings
            install(Auth){
                bearer {
                    loadTokens {
                        BearerTokens(
                            refreshToken = "",
                            accessToken = preferences.getToken() ?: ""
                        )
                    }
                }
            }

            // set basic json headers
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }

            // parse the errors from server
            HttpResponseValidator {
                handleResponseException {
                    when(it){
                        is UnknownHostException -> {
                            throw Exception("check your internet connection")
                        }
                        is ClientRequestException -> {
                            Log.d("Ktor client", "provideClient: it is a client exception");
                            val error: ApiError = json.decodeFromString(it.response.readText())
                            throw Exception(error.message)
                        }
                        is ServerResponseException -> {
                            Log.d("Ktor client", "provideClient: it is a server exception");
                            val error: ApiError = json.decodeFromString(it.response.readText())
                            throw Exception(error.message)
                        }
                        else -> {
                            Log.d("Ktor client", "provideClient: we don't know... ${it.message}")
                        }
                    }
                }
            }
        }
    }
}