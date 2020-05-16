package com.example.mvvmrxtest.core.vm

open class BaseVmState {

    var loading: LoadingState = LoadingState.None
}

sealed class LoadingState {

    data class Error(val error: Throwable) : LoadingState()
    object Loading : LoadingState()
    object None : LoadingState()
}