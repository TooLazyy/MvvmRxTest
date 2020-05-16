package com.example.mvvmrxtest.features.restaurants

import com.example.domain.RestaurantInfo
import com.example.mvvmrxtest.core.vm.BaseVmState
import com.example.mvvmrxtest.core.vm.LoadingState

class RestaurantsState : BaseVmState() {

    val items = mutableListOf<RestaurantInfo>()

    val showFooterLoader: Boolean
        get() = loading !is LoadingState.None
}