package com.example.i_auth.validator

import android.util.Patterns

interface Validator {

    fun isValid(input: String): Boolean
}

class DefaultPasswordValidator : Validator {

    private val MIN_PASSWORD_LENGTH = 3

    override fun isValid(input: String): Boolean =
        input.trim().length >= MIN_PASSWORD_LENGTH
}

class DefaultEmailValidator : Validator {

    override fun isValid(input: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(input).find()
}