package com.example.mvvmrxtest.di

import com.example.mvvmrxtest.features.login.loginModule
import com.example.mvvmrxtest.features.main.mainModule
import com.example.mvvmrxtest.features.restaurants.restaurantsModule
import com.example.mvvmrxtest.features.splash.splashModule
import org.koin.core.module.Module

private val featureModules: List<Module> = listOf(
    mainModule,
    splashModule,
    loginModule,
    restaurantsModule
)

private val coreModules: List<Module> = listOf(
    applicationModule,
    navigationModule,
    authInteractortModule,
    restaurantsInteractorModule
)

val koinModules = coreModules + featureModules
