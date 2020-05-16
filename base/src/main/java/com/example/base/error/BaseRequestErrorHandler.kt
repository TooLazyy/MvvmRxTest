package com.example.base.error

import com.example.base.error.mapper.ErrorMapper
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.functions.Function

abstract class BaseRequestErrorHandler(
    private val errorMapper: ErrorMapper
) {

    fun Completable.wrapError(): Completable =
        this.onErrorResumeNext {
            Completable.error(errorMapper.mapError(it))
        }

    fun <T> Observable<T>.wrapError(): Observable<T> =
        onErrorResumeNext(
            Function<Throwable, ObservableSource<out T>> {
                Observable.error(errorMapper.mapError(it))
            }
        )

    fun <T> Single<T>.wrapError(): Single<T> =
        onErrorResumeNext {
            Single.error(errorMapper.mapError(it))
        }
}