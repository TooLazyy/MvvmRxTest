package com.example.i_restaurants.response

import com.example.domain.RestaurantInfo

data class RestaurantsResponseObj(
    val list: List<RestaurantInfoObj>?
) {

    fun toInfo(): List<RestaurantInfo> =
        list?.map { it.toInfo() } ?: listOf()
}

data class RestaurantInfoObj(
    val name: String?,
    val avatarThumbnail: String?,
    val id: String?,
    val description: String?
) {

    fun toInfo(): RestaurantInfo =
        RestaurantInfo(
            name ?: "",
            avatarThumbnail ?: "",
            id ?: "",
            description ?: ""
        )
}