package com.example.mvvmrxtest.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.base.SafeObserver
import com.example.mvvmrxtest.core.loading.LoadingPlaceholder
import com.example.mvvmrxtest.core.loading.StubLoader
import com.example.mvvmrxtest.core.vm.BaseVm
import com.example.mvvmrxtest.core.vm.BaseVmState
import com.example.mvvmrxtest.core.vm.LoadingState

private const val ARG_FRAGMENT_DESTROYED_ONCE = "arg_fragment_destroyed_once"

abstract class BaseFragment<S : BaseVmState>(
    @LayoutRes
    private val layoutId: Int
) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutId, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments == null) {
            arguments = bundleOf()
        }
        afterCreate(savedInstanceState, isDestroyedOnce(savedInstanceState))
    }

    override fun onDestroyView() {
        setDestroyedOnce()
        super.onDestroyView()
    }

    override fun onDestroy() {
        setDestroyedOnce()
        super.onDestroy()
    }

    protected fun <S : BaseVmState> BaseVm<S>.subscribeToVmState(onStateUpdate: (S) -> Unit) {
        stateData
            .observe(
                this@BaseFragment.viewLifecycleOwner,
                SafeObserver {
                    onStateUpdate(it)
                }
            )
    }

    open fun getLoadingPlaceholder(): LoadingPlaceholder = StubLoader()

    open fun afterCreate(savedInstanceState: Bundle?, recreated: Boolean) {}

    open fun render(state: S) {
        renderLoading(state.loading)
    }

    open fun renderLoading(state: LoadingState) {
        getLoadingPlaceholder().renderLoading(state)
    }

    private fun setDestroyedOnce() {
        arguments?.putBoolean(ARG_FRAGMENT_DESTROYED_ONCE, true)
    }

    private fun isDestroyedOnce(savedInstanceState: Bundle?): Boolean {
        return if (savedInstanceState != null) {
            true
        } else {
            arguments?.getBoolean(ARG_FRAGMENT_DESTROYED_ONCE) ?: false
        }
    }
}