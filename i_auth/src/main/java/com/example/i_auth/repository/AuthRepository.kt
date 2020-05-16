package com.example.i_auth.repository

import io.reactivex.Completable

interface AuthRepository {

    fun loginUser(email: String, password: String, fakeError: Boolean): Completable
}