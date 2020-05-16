package com.example.mvvmrxtest.di

import com.example.i_restaurants.RestaurantsInteractor
import com.example.i_restaurants.repository.FakeRestaurantsRepository
import com.example.i_restaurants.repository.RestaurantsRepository
import org.koin.dsl.module

val restaurantsInteractorModule = module {

    single<RestaurantsRepository> { FakeRestaurantsRepository(get()) }

    single { RestaurantsInteractor(get()) }
}