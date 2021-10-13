package com.moose.foodies.util

import kotlin.random.Random

object RandomIdGenerator {
    private val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray()

    fun getRandom(): String {
        return (0..8).map { chars[(0 until chars.size - 1).random()] }.joinToString(separator = "")
    }
}