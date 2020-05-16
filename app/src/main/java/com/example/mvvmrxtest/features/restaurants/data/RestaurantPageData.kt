package com.example.mvvmrxtest.features.restaurants.data

import com.example.domain.RestaurantInfo

sealed class RestaurantPageData {

    data class Success(val data: List<RestaurantInfo>) : RestaurantPageData()

    data class Failure(val error: Throwable) : RestaurantPageData()
}