package com.example.mvvmrxtest.features.restaurants

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val restaurantsModule = module {

    viewModel { RestaurantsVm() }
}