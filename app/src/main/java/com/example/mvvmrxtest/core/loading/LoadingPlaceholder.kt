package com.example.mvvmrxtest.core.loading

import com.example.mvvmrxtest.core.vm.LoadingState

interface LoadingPlaceholder {

    fun renderLoading(state: LoadingState)
}