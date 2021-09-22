package com.moose.foodies.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

@Serializable
data class ApiError(val message: String)

fun Throwable.parse(): String {
    return when (this){
        is HttpException -> {
            val body = this.response()!!.errorBody()!!.string()
            val error: ApiError = Json.decodeFromString(body)
            return error.message
        }
        is SocketTimeoutException -> "Connection timed out"
        is IOException -> "No connection available"
        else -> this.message!!
    }
}