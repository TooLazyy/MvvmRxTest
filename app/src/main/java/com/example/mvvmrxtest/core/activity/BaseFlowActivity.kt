package com.example.mvvmrxtest.core.activity

import androidx.annotation.LayoutRes
import com.example.navigation.BaseNavigator
import org.koin.android.ext.android.inject
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder

abstract class BaseFlowActivity(
    @LayoutRes
    layoutId: Int
) : BaseActivity(layoutId) {

    private val navigationHolder: NavigatorHolder by inject()

    private val navigator: Navigator by lazy {
        createNavigator()
    }

    abstract val containerId: Int

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    open fun createNavigator(): Navigator = BaseNavigator(this, containerId)
}