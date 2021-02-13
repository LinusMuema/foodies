package com.moose.foodies.features.feature_auth.domain

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(val token: String, val type: String)

@Serializable
data class Credential(val email: String)
