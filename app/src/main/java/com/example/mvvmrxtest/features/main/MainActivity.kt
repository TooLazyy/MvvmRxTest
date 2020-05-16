package com.example.mvvmrxtest.features.main

import android.os.Bundle
import com.example.mvvmrxtest.R
import com.example.mvvmrxtest.core.activity.BaseFlowActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseFlowActivity(R.layout.activity_main) {

    private val vm: MainVm by viewModel()

    override val containerId: Int = R.id.f_content

    override fun afterCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            vm.openInitialScreen()
        }
    }
}
