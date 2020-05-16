package com.example.mvvmrxtest.di

import com.example.i_auth.AuthInteractor
import com.example.i_auth.repository.AuthRepository
import com.example.i_auth.repository.fake.FakeAuthRepository
import com.example.i_auth.repository.storage.AuthStorage
import com.example.i_auth.validator.DefaultEmailValidator
import com.example.i_auth.validator.DefaultPasswordValidator
import com.example.i_auth.validator.Validator
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val AUTH_EMAIL_VALIDATOR = "auth_email_validator"
const val AUTH_PASSWORD_VALIDATOR = "auth_password_validator"

val authInteractortModule = module {

    factory<Validator>(named(AUTH_PASSWORD_VALIDATOR)) { DefaultPasswordValidator() }
    factory<Validator>(named(AUTH_EMAIL_VALIDATOR)) { DefaultEmailValidator() }

    single { AuthStorage(get()) }

    single<AuthRepository> { FakeAuthRepository(get()) }

    single {
        AuthInteractor(
            get(), get(),
            get(named(AUTH_EMAIL_VALIDATOR)),
            get(named(AUTH_PASSWORD_VALIDATOR))
        )
    }
}