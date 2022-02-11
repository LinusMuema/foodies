package com.moose.foodies.data.remote

import android.util.Log
import io.ktor.client.features.logging.*

object FoodiesLogger: Logger {
    private const val tag = "FoodiesLog"
    override fun log(message: String) {
        Log.i(tag, message)
    }
}