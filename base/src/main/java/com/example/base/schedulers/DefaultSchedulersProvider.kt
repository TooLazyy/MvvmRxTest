package com.example.base.schedulers

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DefaultSchedulersProvider : SchedulersProvider {

    override fun main(): Scheduler =
        AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()
}