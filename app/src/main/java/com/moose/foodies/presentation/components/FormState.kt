package com.moose.foodies.presentation.components

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject

open class FormState(val fields: List<TextFieldState>) {

    fun validate(): Boolean = fields.map { it.validate() }.all { it }

    fun getState(name: String): TextFieldState = fields.first { it.name == name }

    inline fun <reified T>getData(): T{
        val json = JSONObject()
        fields.forEach {
            val value = when(it.type){
                is String -> it.text
                is Int -> it.text.toInt()
                is Long -> it.text.toLong()
                is Float -> it.text.toFloat()
                is Double -> it.text.toDouble()
                else -> it.text
            }
            json.put(it.name, value)
        }
        return Json.decodeFromString(json.toString())
    }
}