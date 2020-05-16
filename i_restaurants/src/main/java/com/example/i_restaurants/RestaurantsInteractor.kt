package com.example.i_restaurants

import com.example.domain.RestaurantInfo
import com.example.i_restaurants.repository.RestaurantsRepository
import com.example.i_restaurants.request.LoadRestaurantsRequest
import io.reactivex.Single

class RestaurantsInteractor(
    private val repository: RestaurantsRepository
) {

    fun loadRestaurants(page: Int, fakeError: Boolean): Single<List<RestaurantInfo>> =
        repository.loadRestaurants(LoadRestaurantsRequest(page), fakeError)
}