package com.example.mvvmrxtest.features.login

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    viewModel { LoginVm() }
}