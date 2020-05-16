package com.example.mvvmrxtest.features.restaurants.controller

import android.view.ViewGroup
import com.example.mvvmrxtest.R
import com.example.mvvmrxtest.core.loading.LoadingView
import com.example.mvvmrxtest.core.vm.LoadingState
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

private const val LOADER_ID = "31872373182"

class RestaurantsLoadingController(
    private val onRetryAction: () -> Unit
) : BindableItemController<LoadingState, RestaurantsLoadingController.Holder>() {

    override fun getItemId(item: LoadingState): String = LOADER_ID

    override fun createViewHolder(parent: ViewGroup?): Holder =
        Holder(parent)

    inner class Holder(parent: ViewGroup?) : BindableViewHolder<LoadingState>(parent, R.layout.item_restaurant_loader) {

        override fun bind(item: LoadingState) {
            (itemView as LoadingView).run {
                renderLoading(item)
                onRetryClicked = onRetryAction
            }
        }
    }
}