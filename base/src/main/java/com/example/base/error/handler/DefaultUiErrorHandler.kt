package com.example.base.error.handler

import com.example.base.R
import com.example.base.error.NoInternetError
import com.example.base.message.MessageController

class DefaultUiErrorHandler(
    private val messageController: MessageController
) : UiErrorHandler {

    //TODO more errors
    override fun handleError(error: Throwable) {
        when(error) {
            is NoInternetError -> messageController.showToast(R.string.error_no_internet)
            else -> messageController.showToast(R.string.error_unknown)
        }
    }
}