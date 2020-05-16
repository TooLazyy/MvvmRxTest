package com.example.mvvmrxtest.features.restaurants

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmrxtest.R
import com.example.mvvmrxtest.core.fragment.BaseFragment
import com.example.mvvmrxtest.core.utils.onLoadMoreListener
import com.example.mvvmrxtest.features.restaurants.controller.RestaurantController
import com.example.mvvmrxtest.features.restaurants.controller.RestaurantsLoadingController
import kotlinx.android.synthetic.main.fragment_restaurants.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class RestaurantsFragment : BaseFragment<RestaurantsState>(R.layout.fragment_restaurants) {

    companion object {

        const val TAG = "RestaurantsFragment"
    }

    private val vm: RestaurantsVm by viewModel()
    private val easyAdapter = EasyAdapter()
    private val itemController = RestaurantController()
    private val footerController = RestaurantsLoadingController(::onRetryLoad)

    override fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {
        initRecycler()
        vm.subscribeToVmState(::render)
        if (!recreated) {
            vm.onFirstLoad()
        }
    }

    override fun render(state: RestaurantsState) {
        easyAdapter
            .setItems(
                ItemList
                    .create()
                    .addAll(
                        state.items,
                        itemController
                    )
                    .addIf(
                        state.showFooterLoader,
                        state.loading,
                        footerController
                    )
            )
    }

    private fun initRecycler() {
        rv_restaurants.run {
            adapter = easyAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            onLoadMoreListener {
                vm.loadMore(cb_fake_error.isChecked)
            }
        }
    }

    private fun onRetryLoad() {
        vm.retryLoad(cb_fake_error.isChecked)
    }
}