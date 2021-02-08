package com.moose.foodies.features.feature_auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(val token: String)

@Serializable
data class Credential(val email: String)
