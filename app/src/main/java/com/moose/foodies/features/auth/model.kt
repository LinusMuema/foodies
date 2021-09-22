package com.moose.foodies.features.auth

import kotlinx.serialization.Serializable

@Serializable
data class Auth(val message: String, val token: String = "")

@Serializable
data class Credentials(val email: String, val password: String)