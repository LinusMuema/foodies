package com.moose.foodies.util

sealed class Result <out T>{
    data class Success<out R>(val value: R): Result<R>()
    data class Error(val message: String?): Result<Nothing>()
    data class Idle(val message: String? = null) : Result<Nothing>()
}

inline fun <reified T> Result<T>.onError(callback: (error: String?) -> Unit) {
    if (this is Result.Error) callback(message)
}

inline fun <reified T> Result<T>.onSuccess(callback: (value: T) -> Unit) {
    if (this is Result.Success) callback(value)
}