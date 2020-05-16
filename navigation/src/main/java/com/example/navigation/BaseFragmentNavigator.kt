package com.example.navigation

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.*
import java.util.*

open class BaseFragmentNavigator(
    private val fragment: Fragment,
    private val containerId: Int
) : Navigator {

    private val fragmentManager: FragmentManager = fragment.childFragmentManager
    private var localStackCopy: LinkedList<String?> = LinkedList()

    override fun applyCommands(commands: Array<out Command>?) {
        fragmentManager.executePendingTransactions()

        copyStackToLocal()

        commands?.forEach { applyCommand(it) }
    }

    private fun applyCommand(command: Command) {
        when (command) {
            is Forward -> activityForward(command)
            is Replace -> activityReplace(command)
            is BackTo -> backTo(command)
            is Back -> fragmentBack()
        }
    }

    private fun fragmentBack() {
        if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.removeLast()
        } else {
            activityBack()
        }
    }

    private fun activityBack() {
        val activity = fragment.activity ?: return
        activity.finishAfterTransition()
    }

    private fun backTo(command: BackTo) {
        if (command.screen == null) {
            backToRoot()
        } else {
            val key = command.screen.screenKey
            val index = localStackCopy.indexOf(key)
            val size = localStackCopy.size

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy.removeLast()
                }
                fragmentManager.popBackStack(key, 0)
            } else {
                backToUnexisting(command.screen as SupportAppScreen)
            }
        }
    }

    private fun activityReplace(command: Replace) {
        val activity = fragment.activity ?: return
        val route = command.screen as SupportAppScreen
        val activityIntent = route.getActivityIntent(activity)

        // Replace activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(route)
            checkAndStartActivity(route, activityIntent, options)
            activity.finishAfterTransition()
        } else {
            fragmentReplace(command)
        }
    }

    private fun fragmentReplace(command: Replace) {
        val route = command.screen as SupportAppScreen
        val fragment = createFragment(route)

        if (localStackCopy.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy.removeLast()

            val fragmentTransaction = fragmentManager.beginTransaction()

            setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction,
                route
            )

            fragmentTransaction
                .replace(containerId, fragment, route.screenKey)
                .addToBackStack(route.screenKey)
                .commit()
            localStackCopy.add(route.screenKey)

        } else {
            val fragmentTransaction = fragmentManager.beginTransaction()

            setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                fragment,
                fragmentTransaction,
                route
            )

            fragmentTransaction
                .replace(containerId, fragment, route.screenKey)
                .commit()
        }
    }

    private fun activityForward(command: Forward) {
        val activity = fragment.activity ?: return
        val route = command.screen as SupportAppScreen
        val activityIntent = route.getActivityIntent(activity)

        when {
            // Start activity
            activityIntent != null -> {
                val options = createStartActivityOptions(route)
                checkAndStartActivity(route, activityIntent, options)
            }
            else -> fragmentForward(command)
        }
    }

    private fun fragmentForward(command: Forward) {
        val route = command.screen as SupportAppScreen
        val fragment = createFragment(route)

        val fragmentTransaction = fragmentManager.beginTransaction()

        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId),
            fragment,
            fragmentTransaction,
            route
        )

        fragmentTransaction
            .replace(containerId, fragment, route.screenKey)
            .addToBackStack(route.screenKey)
            .commit()
        localStackCopy.add(route.screenKey)
    }

    /**
     * Creates Fragment matching `screenKey`.
     *
     * @param screen screen
     * @return instantiated fragment for the passed screen
     */
    private fun createFragment(screen: SupportAppScreen): Fragment {
        val fragment = screen.fragment

        if (fragment == null) {
            errorWhileCreatingScreen(screen)
        }
        return fragment!!
    }

    /**
     * Override this method to setup fragment transaction [FragmentTransaction].
     * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
     *
     * @param command             current navigation command. Will be only [Forward] or [Replace]
     * @param currentFragment     current fragment in container
     * (for [Replace] command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param fragmentTransaction fragment transaction
     * @param route command route
     */
    private fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment,
        fragmentTransaction: FragmentTransaction,
        route: SupportAppScreen
    ) {
    }

    private fun createStartActivityOptions(route: SupportAppScreen): Bundle? = null

    private fun backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStackCopy.clear()
    }

    private fun copyStackToLocal() {
        localStackCopy = LinkedList()

        val stackSize = fragmentManager.backStackEntryCount
        for (i in 0 until stackSize) {
            localStackCopy.add(fragmentManager.getBackStackEntryAt(i).name)
        }
    }

    private fun checkAndStartActivity(
        screen: SupportAppScreen,
        activityIntent: Intent,
        options: Bundle?
    ) {
        val activity = fragment.activity ?: return
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(activityIntent, options)
        } else {
            unexistingActivity(screen, activityIntent)
        }
    }

    private fun unexistingActivity(screen: SupportAppScreen, activityIntent: Intent) {
        // Do nothing by default
    }

    private fun backToUnexisting(screen: SupportAppScreen) {
        backToRoot()
    }

    private fun errorWhileCreatingScreen(screen: SupportAppScreen) {
        throw RuntimeException("Can't create a screen: " + screen.screenKey)
    }
}