package com.moose.foodies.util

import com.google.gson.GsonBuilder
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ExceptionParser {

    data class ApiError(val message: String)

    fun parse(e: Throwable): String{
        return when (e){
            is HttpException -> {
                val body = e.response()!!.errorBody()!!.string()
                return GsonBuilder().create().fromJson(body, ApiError::class.java).message
            }
            is SocketTimeoutException -> "Connection timed out"
            is IOException -> "A network error occurred"
            else -> e.message!!
        }
    }

}