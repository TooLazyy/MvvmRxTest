package com.example.i_restaurants.repository

import com.example.base.error.BaseRequestErrorHandler
import com.example.base.error.mapper.ErrorMapper
import com.example.domain.RestaurantInfo
import com.example.i_restaurants.request.ITEMS_PER_PAGE
import com.example.i_restaurants.request.LoadRestaurantsRequest
import com.example.i_restaurants.response.RestaurantInfoObj
import com.example.i_restaurants.response.RestaurantsResponseObj
import io.reactivex.Single
import java.net.UnknownHostException
import java.util.*
import java.util.concurrent.TimeUnit

private const val MAX_PAGES = 5
private const val FAKE_DELAY = 2000L

class FakeRestaurantsRepository(
    errorMapper: ErrorMapper
) : BaseRequestErrorHandler(errorMapper),
    RestaurantsRepository {

    private val fakeDataGenerator = FakeRestaurantsGenerator()

    override fun loadRestaurants(
        request: LoadRestaurantsRequest,
        fakeError: Boolean
    ): Single<List<RestaurantInfo>> =
        Single.timer(FAKE_DELAY, TimeUnit.MILLISECONDS)
            .map {
                if (fakeError) {
                    throw UnknownHostException()
                }
                fakeDataGenerator.generateData(request)
            }
            .map { it.toInfo() }
            .wrapError()
}

class FakeRestaurantsGenerator {

    fun generateData(request: LoadRestaurantsRequest): RestaurantsResponseObj {
        val result = mutableListOf<RestaurantInfoObj>()
        if (!isMaxPagesExceeded(request.page)) {
            fillPage(request.page, result)
        }
        return RestaurantsResponseObj(result)
    }

    private fun isMaxPagesExceeded(page: Int): Boolean =
        page > MAX_PAGES

    private fun fillPage(page: Int, result: MutableList<RestaurantInfoObj>) {
        for (i in 0 until ITEMS_PER_PAGE) {
            val realPosition = page * ITEMS_PER_PAGE + i + 1 - ITEMS_PER_PAGE
            result.add(
                RestaurantInfoObj(
                    "Some restaurant name $realPosition",
                    "",
                    UUID.randomUUID().toString(),
                    "Some restaurant description $realPosition"
                )
            )
        }
    }
}