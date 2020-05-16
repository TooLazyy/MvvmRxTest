package com.example.mvvmrxtest.features.login

import com.example.i_auth.AuthInteractor
import com.example.mvvmrxtest.core.vm.BaseVm
import com.example.mvvmrxtest.core.vm.LoadingState
import com.example.mvvmrxtest.features.restaurants.RestaurantsScreen
import org.koin.core.inject

class LoginVm : BaseVm<LoginState>() {

    private val interactor: AuthInteractor by inject()

    override val state: LoginState = LoginState()

    fun onEmailChanged(email: String) {
        state.isEmailValid = interactor.validateEmail(email)
        render()
    }

    fun onPasswordChanged(password: String) {
        state.isPasswordValid = interactor.validatePassword(password)
        render()
    }

    fun onLoginClicked(
        email: String,
        password: String,
        fakeError: Boolean
    ) {
        state.loading = LoadingState.Loading
        render()

        subscribeIo(
            interactor
                .loginUser(email, password, fakeError),
            {
                onLoginCompleted()
            },
            {
                state.loading = LoadingState.None
                render()
            }
        )
    }

    private fun onLoginCompleted() {
        router.newRootScreen(RestaurantsScreen)
    }
}