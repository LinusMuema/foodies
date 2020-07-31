package com.moose.foodies.util

import com.google.gson.GsonBuilder
import com.moose.foodies.models.AuthResponse
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

object ExceptionParser {

    fun parse(e: Throwable): String{
        return when (e){
            is HttpException -> {
                val body = e.response()!!.errorBody()!!.string()
                return GsonBuilder().create().fromJson(body, AuthResponse::class.java).reason
            }
            is SocketTimeoutException -> "Connection timed out"
            is IOException -> "A network error occurred"
            else -> {
                e.message!!
            }
        }
    }

}