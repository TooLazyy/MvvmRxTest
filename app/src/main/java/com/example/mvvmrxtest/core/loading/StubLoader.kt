package com.example.mvvmrxtest.core.loading

import com.example.mvvmrxtest.core.vm.LoadingState

class StubLoader : LoadingPlaceholder {

    override fun renderLoading(state: LoadingState) {}
}