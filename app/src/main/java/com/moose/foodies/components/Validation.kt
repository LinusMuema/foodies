package com.moose.foodies.components

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.regex.Pattern

enum class Validation { REQUIRED, EMAIL }

interface Validators {

    fun required(value: String): Boolean

    fun email(value: String): Boolean
}

class ValidatorsImpl : Validators {
    private val pattern: Pattern = Pattern.compile(".+@.+\\.[a-z]+")

    override fun required(value: String): Boolean = value.isNotEmpty()

    override fun email(value: String): Boolean = pattern.matcher(value).matches()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ValidatorBinding{

    @Binds
    abstract fun provideValidator(validatorsImpl: ValidatorsImpl): Validators
}
