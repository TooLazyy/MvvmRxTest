package com.example.mvvmrxtest.core.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.base.SafeObserver
import com.example.mvvmrxtest.core.vm.BaseVm
import com.example.mvvmrxtest.core.vm.BaseVmState

abstract class BaseActivity(
    @LayoutRes
    private val layoutId: Int
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        afterCreate(savedInstanceState)
    }

    open fun afterCreate(savedInstanceState: Bundle?) {}

    protected fun <S : BaseVmState> BaseVm<S>.subscribeToVmState(onStateUpdate: (S) -> Unit) {
        stateData
            .observe(
                this@BaseActivity,
                SafeObserver {
                    onStateUpdate(it)
                }
            )
    }
}