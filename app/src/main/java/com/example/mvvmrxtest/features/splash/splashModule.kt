package com.example.mvvmrxtest.features.splash

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {

    viewModel { SplashVm() }
}