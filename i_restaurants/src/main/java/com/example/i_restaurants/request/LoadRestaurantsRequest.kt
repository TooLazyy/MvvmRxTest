package com.example.i_restaurants.request

const val ITEMS_PER_PAGE = 30

data class LoadRestaurantsRequest(
    val page: Int
)