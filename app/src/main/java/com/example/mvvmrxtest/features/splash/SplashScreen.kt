package com.example.mvvmrxtest.features.splash

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object SplashScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = SplashFragment()

    override fun getScreenKey(): String = SplashFragment.TAG
}