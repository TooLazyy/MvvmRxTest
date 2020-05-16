package com.example.base.message

import android.widget.Toast
import com.example.base.activity.CurrentActivityHolder

class DefaultMessageController(
    private val activityHolder: CurrentActivityHolder
) : MessageController {

    private var toast: Toast? = null

    override fun showToast(text: String, duration: Int) {
        cancelToast()
        activityHolder.currentActivity?.let {
            toast = Toast.makeText(it, text, duration)
            toast?.show()
        }
    }

    override fun showToast(textResId: Int, duration: Int) {
        cancelToast()
        activityHolder.currentActivity?.let {
            toast = Toast.makeText(it, it.getString(textResId), duration)
            toast?.show()
        }
    }

    override fun cancelAll() {
        cancelToast()
    }

    private fun cancelToast() {
        toast?.cancel()
        toast = null
    }
}