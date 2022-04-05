package com.moose.foodies.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(val email: String, val password: String)