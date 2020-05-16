package com.example.mvvmrxtest

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.example.base.activity.CurrentActivityHolder
import com.example.base.activity.EmptyActivityLifecycleCallbacks
import com.example.base.message.MessageController
import com.example.mvvmrxtest.di.koinModules
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestApp : Application() {

    private val activityHolder: CurrentActivityHolder by inject()
    private val messageController: MessageController by inject()

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initActivityHolder()
    }

    private fun initKoin() {
        startKoin {
            androidContext(applicationContext)
            androidLogger()
            modules(koinModules)
        }
    }

    private fun initActivityHolder() {
        registerActivityLifecycleCallbacks(object : EmptyActivityLifecycleCallbacks() {

            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                activityHolder.currentActivity = activity
            }

            override fun onActivityPaused(activity: Activity) {
                messageController.cancelAll()
                activityHolder.currentActivity = null
            }

            override fun onActivityResumed(activity: Activity) {
                activityHolder.currentActivity = activity
            }
        })
    }
}