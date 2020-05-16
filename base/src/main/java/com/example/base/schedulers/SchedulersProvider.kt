package com.example.base.schedulers

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun main(): Scheduler

    fun io(): Scheduler
}