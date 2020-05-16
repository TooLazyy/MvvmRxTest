package com.example.mvvmrxtest.features.restaurants

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object RestaurantsScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = RestaurantsFragment()

    override fun getScreenKey(): String = RestaurantsFragment.TAG
}