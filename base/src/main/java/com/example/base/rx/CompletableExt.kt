package com.example.base.rx

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver

fun disposableCompletable(
    onComplete: Action = Action { },
    onError: Consumer<Throwable> = Consumer { }
): DisposableCompletableObserver {

    return object : DisposableCompletableObserver() {

        override fun onComplete() {
            onComplete.run()
        }

        override fun onError(e: Throwable) {
            onError.accept(e)
        }
    }
}

fun <T> disposableObservable(
    onNext: Consumer<T> = Consumer { },
    onComplete: Action = Action { },
    onError: Consumer<Throwable> = Consumer { }
): DisposableObserver<T> {

    return object : DisposableObserver<T>() {

        override fun onComplete() {
            onComplete.run()
        }

        override fun onNext(t: T) {
            onNext.accept(t)
        }

        override fun onError(e: Throwable) {
            onError.accept(e)
        }
    }
}