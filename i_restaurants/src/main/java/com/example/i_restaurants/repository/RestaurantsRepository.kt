package com.example.i_restaurants.repository

import com.example.domain.RestaurantInfo
import com.example.i_restaurants.request.LoadRestaurantsRequest
import io.reactivex.Single

interface RestaurantsRepository {

    fun loadRestaurants(request: LoadRestaurantsRequest, fakeError: Boolean): Single<List<RestaurantInfo>>
}