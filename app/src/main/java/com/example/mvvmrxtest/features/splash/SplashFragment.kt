package com.example.mvvmrxtest.features.splash

import android.os.Bundle
import com.example.mvvmrxtest.R
import com.example.mvvmrxtest.core.fragment.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashState>(R.layout.fragment_splash) {

    companion object {

        const val TAG = "SplashFragment"
    }

    private val vm: SplashVm by viewModel()

    override fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {
        if (!recreated) {
            vm.openNextScreen()
        }
    }
}