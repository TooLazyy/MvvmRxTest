package com.example.i_auth.repository.fake

import com.example.base.error.BaseRequestErrorHandler
import com.example.base.error.mapper.ErrorMapper
import com.example.i_auth.repository.AuthRepository
import io.reactivex.Completable
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class FakeAuthRepository(
    errorMapper: ErrorMapper
) : BaseRequestErrorHandler(errorMapper),
    AuthRepository {

    override fun loginUser(
        email: String,
        password: String,
        fakeError: Boolean
    ): Completable =
        Completable.timer(2000, TimeUnit.MILLISECONDS)
            .andThen {
                if (fakeError) {
                    it.onError(UnknownHostException())
                } else {
                    it.onComplete()
                }
            }
            .wrapError()
}