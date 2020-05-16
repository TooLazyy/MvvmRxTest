package com.example.mvvmrxtest.features.login

import com.example.mvvmrxtest.core.vm.BaseVmState

class LoginState : BaseVmState() {

    var isEmailValid = false
    var isPasswordValid = false

    val isLoginButtonEnabled: Boolean
        get() = isEmailValid && isPasswordValid
}