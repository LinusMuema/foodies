package com.moose.foodies.presentation.components

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

open class FormState(val fields: List<TextFieldState<*>>) {

    fun validate(): Boolean = fields.map { it.validate() }.all { it }

    fun getState(name: String): TextFieldState<*> = fields.first { it.name == name }

    inline fun <reified T>getData(): T {
        val map = fields.associate {
            val value = if (it.transform == null) it.text else it.transform!!(it.text)
            it.name to value
        }
        val json = Json { ignoreUnknownKeys = true }
        val data = JSONObject(map).toString()
        return json.decodeFromString(data)
    }
}