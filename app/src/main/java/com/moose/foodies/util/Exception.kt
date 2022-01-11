package com.moose.foodies.util

import kotlinx.serialization.Serializable
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