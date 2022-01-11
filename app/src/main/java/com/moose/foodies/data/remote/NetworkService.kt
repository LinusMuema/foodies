package com.moose.foodies.data.remote

import android.util.Log
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
import java.lang.Exception
import kotlinx.serialization.json.Json as json


@EntryPoint
@InstallIn(SingletonComponent::class)
interface ClientHelper {
    fun client(): HttpClient
}

private object CustomLogger: Logger {
    private const val tag = "FoodiesLog"
    override fun log(message: String) {
        Log.i(tag, message)
    }
}

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
                logger = CustomLogger
            }

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
                if (method != HttpMethod.Get) contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }

            // parse the errors from server
            HttpResponseValidator {
                handleResponseException {
                    when(it){
                        is ClientRequestException -> {
                            val error: ApiError = json.decodeFromString(it.response.readText())
                            throw Exception(error.message)
                        }
                        is ServerResponseException -> {
                            val error: ApiError = json.decodeFromString(it.response.readText())
                            throw Exception(error.message)
                        }
                        else -> return@handleResponseException
                    }
                }
            }
        }
    }
}