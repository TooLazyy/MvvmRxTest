package com.example.mvvmrxtest.features.restaurants.pager

import com.example.base.schedulers.SchedulersProvider
import com.example.domain.RestaurantInfo
import com.example.i_restaurants.RestaurantsInteractor
import com.example.i_restaurants.request.ITEMS_PER_PAGE
import com.example.mvvmrxtest.features.restaurants.data.RestaurantPageData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject

class RestaurantsPager(
    private val vmDisposables: CompositeDisposable
) : KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val restaurantsInteractor: RestaurantsInteractor by inject()

    private var currentPage = 1
    private var isWaitingForData = false
    private var isLastPageLoaded = false
    private val loadingStateSubject = PublishSubject.create<Boolean>()
    private val dataStateSubject = PublishSubject.create<RestaurantPageData>()

    val loadingStateObservable: Observable<Boolean>
        get() = loadingStateSubject.hide()

    val dataStateObservable: Observable<RestaurantPageData>
        get() = dataStateSubject.hide()

    fun loadNextPage(fakeError: Boolean) {
        if (isWaitingForData || isLastPageLoaded) {
            return
        }
        loadPage(fakeError)
    }

    fun loadPage(fakeError: Boolean) {
        isWaitingForData = true
        loadingStateSubject.onNext(isWaitingForData)

        restaurantsInteractor
            .loadRestaurants(currentPage, fakeError)
            .subscribeOn(schedulersProvider.io())
            .subscribe(
                {
                    onPageLoaded(it)
                },
                {
                    dataStateSubject.onNext(RestaurantPageData.Failure(it))
                }
            ).apply {
                vmDisposables.add(this)
            }
    }

    private fun onPageLoaded(data: List<RestaurantInfo>) {
        currentPage++
        isLastPageLoaded = data.size < ITEMS_PER_PAGE
        isWaitingForData = false

        loadingStateSubject.onNext(isWaitingForData)
        dataStateSubject.onNext(RestaurantPageData.Success(data))
    }
}