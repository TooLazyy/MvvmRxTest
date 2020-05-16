package com.example.mvvmrxtest.features.restaurants

import com.example.mvvmrxtest.core.vm.BaseVm
import com.example.mvvmrxtest.core.vm.LoadingState
import com.example.mvvmrxtest.features.restaurants.data.RestaurantPageData
import com.example.mvvmrxtest.features.restaurants.pager.RestaurantsPager

class RestaurantsVm : BaseVm<RestaurantsState>() {

    private val pager = RestaurantsPager(disposables)

    override val state: RestaurantsState = RestaurantsState()

    init {
        subscribeToPageState()
        subscribeToPageLoadingState()
    }

    fun onFirstLoad() {
        pager.loadPage(false)
    }

    fun loadMore(fakeError: Boolean) {
        pager.loadNextPage(fakeError)
    }

    fun retryLoad(fakeError: Boolean) {
        pager.loadPage(fakeError)
    }

    private fun subscribeToPageState() {
        subscribeUi(
            pager.dataStateObservable
        ) {
            when (it) {
                is RestaurantPageData.Success -> {
                    state.items.addAll(it.data)
                    state.loading = LoadingState.None
                    render()
                }
                is RestaurantPageData.Failure -> {
                    state.loading = LoadingState.Error(it.error)
                    render()
                    onHandleError(it.error)
                }
            }
        }
    }

    private fun subscribeToPageLoadingState() {
        subscribeUi(
            pager.loadingStateObservable
        ) {
            if (it) {
                state.loading = LoadingState.Loading
                render()
            }
        }
    }
}