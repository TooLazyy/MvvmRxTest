package com.example.mvvmrxtest.core.vm

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.base.error.handler.UiErrorHandler
import com.example.base.message.MessageController
import com.example.base.rx.disposableCompletable
import com.example.base.rx.disposableObservable
import com.example.base.rx.toAction
import com.example.base.rx.toConsumer
import com.example.base.schedulers.SchedulersProvider
import com.example.navigation.GlobalRouter
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseVm<S : BaseVmState> :
    ViewModel(),
    KoinComponent {

    protected val router: GlobalRouter by inject()
    protected val schedulers: SchedulersProvider by inject()
    protected val messageController: MessageController by inject()
    protected val errorHandler: UiErrorHandler by inject()

    protected val disposables = CompositeDisposable()

    protected val stateLiveData = MutableLiveData<S>()
    val stateData: LiveData<S>
        get() = stateLiveData

    abstract val state: S

    override fun onCleared() {
        disposables.clear()
    }

    open fun onHandleError(e: Throwable) {
        errorHandler.handleError(e)
    }

    @MainThread
    protected fun render() {
        stateLiveData.value = state
    }

    protected fun Disposable.bind() {
        disposables.add(this)
    }

    protected fun <T> subscribeUi(
        source: Observable<T>,
        onNext: (T) -> Unit
    ): Disposable = subscribeUi(source, onNext, {})

    protected fun <T> subscribeUi(
        source: Observable<T>,
        onNext: (T) -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        val error =
            Consumer<Throwable> { t ->
                handleError(t, onError.toConsumer())
            }
        val next = Consumer { t: T ->
            onNext(t)
        }
        return source
            .observeOn(schedulers.main())
            .subscribeWith(
                disposableObservable<T>(
                    onNext = next,
                    onError = error
                )
            ).also {
                it.bind()
            }
    }

    protected fun subscribeUi(
        source: Completable,
        onComplete: () -> Unit
    ): Disposable = subscribeUi(source, onComplete, {})

    protected fun subscribeUi(
        source: Completable,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        val error =
            Consumer<Throwable> { t -> handleError(t, onError.toConsumer()) }
        return source
            .observeOn(schedulers.main())
            .subscribeWith(
                disposableCompletable(onComplete.toAction(), error)
            ).also {
                it.bind()
            }
    }

    protected fun subscribeIo(
        source: Completable,
        onComplete: () -> Unit
    ): Disposable = subscribeIo(source, onComplete, {})

    protected fun subscribeIo(
        source: Completable,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    ): Disposable {
        val error =
            Consumer<Throwable> { t -> handleError(t, onError.toConsumer()) }
        return source
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .subscribeWith(
                disposableCompletable(onComplete.toAction(), error)
            ).also {
                it.bind()
            }
    }

    fun exit() {
        router.exit()
    }

    private fun handleError(e: Throwable, onError: Consumer<Throwable>?) {
        onHandleError(e)
        onError?.accept(e)
    }
}