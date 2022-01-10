package com.moose.foodies.util

import android.util.Log
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
        is SocketTimeoutException -> "Connection timed out"
        is IOException -> "No connection available"
        else -> this.message!!
    }
}