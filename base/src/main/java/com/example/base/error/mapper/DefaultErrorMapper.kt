package com.example.base.error.mapper

import com.example.base.error.NoInternetError
import com.example.base.error.UnspecifiedError
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class DefaultErrorMapper : ErrorMapper {

    //TODO more errors
    override fun mapError(error: Throwable): Throwable {
        return when (error) {
            is UnknownHostException,
            is SocketTimeoutException -> NoInternetError()
            else -> UnspecifiedError()
        }
    }
}