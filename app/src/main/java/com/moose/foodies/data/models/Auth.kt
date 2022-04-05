package com.moose.foodies.data.models

import com.moose.foodies.domain.models.Profile
import kotlinx.serialization.Serializable

@Serializable
data class Auth(val message: String, val token: String = "", val user: Profile)