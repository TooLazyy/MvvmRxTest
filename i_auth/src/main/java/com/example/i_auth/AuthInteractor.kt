package com.example.i_auth

import com.example.i_auth.repository.AuthRepository
import com.example.i_auth.repository.storage.AuthStorage
import com.example.i_auth.validator.Validator
import io.reactivex.Completable

class AuthInteractor(
    private val repository: AuthRepository,
    private val storage: AuthStorage,
    private val emailValidator: Validator,
    private val passwordValidator: Validator
) {

    val isUserLoggedIn: Boolean
        get() = storage.isUserLoggedIn

    fun validateEmail(email: String): Boolean =
        emailValidator.isValid(email)

    fun validatePassword(password: String): Boolean =
        passwordValidator.isValid(password)

    fun loginUser(
        email: String,
        password: String,
        fakeError: Boolean
    ): Completable =
        repository
            .loginUser(email, password, fakeError)
            .doOnComplete {
                storage.isUserLoggedIn = true
            }
}