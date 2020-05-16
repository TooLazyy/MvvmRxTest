package com.example.mvvmrxtest.features.main

import com.example.mvvmrxtest.core.vm.BaseVm
import com.example.mvvmrxtest.features.splash.SplashScreen

class MainVm : BaseVm<MainState>() {

    override val state: MainState = MainState()

    fun openInitialScreen() {
        router.newRootScreen(SplashScreen)
    }
}