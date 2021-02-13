package com.moose.foodies.util

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ExceptionParser {

    @Serializable
    data class ApiError(val message: String)

    fun parse(e: Throwable): String{
        return when (e){
            is HttpException -> {
                val body = e.response()!!.errorBody()!!.string()
                val error: ApiError = Json.decodeFromString(body)
                return error.message
            }
            is SocketTimeoutException -> "Connection timed out"
            is IOException -> "A network error occurred"
            else -> e.message!!
        }
    }

}