package com.example.mvvmrxtest.features.splash

import com.example.i_auth.AuthInteractor
import com.example.mvvmrxtest.core.vm.BaseVm
import com.example.mvvmrxtest.features.login.LoginScreen
import com.example.mvvmrxtest.features.restaurants.RestaurantsScreen
import io.reactivex.Completable
import org.koin.core.inject
import java.util.concurrent.TimeUnit

private const val SPLASH_DELAY = 1500L

class SplashVm : BaseVm<SplashState>() {

    private val authInteractor: AuthInteractor by inject()

    override val state: SplashState = SplashState()

    fun openNextScreen() {
        subscribeUi(
            Completable.timer(SPLASH_DELAY, TimeUnit.MILLISECONDS)
        ) {
            router.newRootScreen(
                if (authInteractor.isUserLoggedIn) {
                    RestaurantsScreen
                } else {
                    LoginScreen
                }
            )
        }
    }
}