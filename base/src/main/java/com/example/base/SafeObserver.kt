package com.example.base

import androidx.lifecycle.Observer

class SafeObserver<T>(
    private val onNewValue: (T) -> Unit
) : Observer<T> {

    override fun onChanged(t: T?) {
        if (t != null) {
            onNewValue(t)
        }
    }
}