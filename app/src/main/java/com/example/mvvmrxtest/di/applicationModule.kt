package com.example.mvvmrxtest.di

import android.content.Context
import com.example.base.activity.CurrentActivityHolder
import com.example.base.error.handler.DefaultUiErrorHandler
import com.example.base.error.handler.UiErrorHandler
import com.example.base.error.mapper.DefaultErrorMapper
import com.example.base.error.mapper.ErrorMapper
import com.example.base.message.DefaultMessageController
import com.example.base.message.MessageController
import com.example.base.schedulers.DefaultSchedulersProvider
import com.example.base.schedulers.SchedulersProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val STORAGE_PREFERENCES = "storage_preferences"

val applicationModule = module {

    single<SchedulersProvider> { DefaultSchedulersProvider() }

    single {
        androidContext()
            .getSharedPreferences(STORAGE_PREFERENCES, Context.MODE_PRIVATE)
    }

    single { CurrentActivityHolder() }
    single<MessageController> { DefaultMessageController(get()) }
    single<UiErrorHandler> { DefaultUiErrorHandler(get()) }
    single<ErrorMapper> { DefaultErrorMapper() }
}