package com.example.base.rx

import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

fun <T> ((T) -> Unit).toConsumer(): Consumer<T> {
    return Consumer { this.invoke(it) }
}

fun (() -> Unit).toAction(): Action {
    return Action { this.invoke() }
}