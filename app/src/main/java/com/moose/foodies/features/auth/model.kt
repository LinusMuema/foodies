package com.moose.foodies.features.auth

data class Auth(val message: String, val token: String)

data class Credentials(val email: String, val password: String)