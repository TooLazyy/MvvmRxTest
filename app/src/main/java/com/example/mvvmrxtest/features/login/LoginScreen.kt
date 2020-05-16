package com.example.mvvmrxtest.features.login

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object LoginScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = LoginFragment()

    override fun getScreenKey(): String = LoginFragment.TAG
}