package com.moose.foodies.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Auth(val message: String, val token: String = "", val user: Profile)

@Serializable
data class Credentials(val email: String, val password: String)