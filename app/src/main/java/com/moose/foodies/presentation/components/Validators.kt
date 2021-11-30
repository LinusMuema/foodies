package com.moose.foodies.presentation.components

private const val EMAIL_MESSAGE = "invalid email address"
private const val REQUIRED_MESSAGE = "this field is required"

sealed interface Validators
open class Email(var message: String = EMAIL_MESSAGE): Validators
open class Min(var message: String, var limit: Double): Validators
open class Max(var message: String, var limit: Double): Validators
open class Regex(var message: String, var regex: String): Validators
open class Required(var message: String = REQUIRED_MESSAGE): Validators