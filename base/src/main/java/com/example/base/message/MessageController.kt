package com.example.base.message

import android.widget.Toast
import androidx.annotation.StringRes

interface MessageController {

    fun showToast(
        text: String,
        duration: Int = Toast.LENGTH_LONG
    )

    fun showToast(
        @StringRes textResId: Int,
        duration: Int = Toast.LENGTH_LONG
    )

    fun cancelAll()
}