package com.example.mvvmrxtest.core.loading

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.mvvmrxtest.R
import com.example.mvvmrxtest.core.vm.LoadingState
import kotlinx.android.synthetic.main.view_loading.view.*

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle),
    LoadingPlaceholder {

    private val colorLoading: Int = Color.WHITE

    var onRetryClicked = {}

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this)
        isClickable = true
        btn_retry.setOnClickListener {
            onRetryClicked()
        }
        ViewCompat.setElevation(this, 10F)
    }

    override fun renderLoading(state: LoadingState) {
        when (state) {
            is LoadingState.None -> isVisible = false
            is LoadingState.Loading -> showWithBackground(colorLoading)
            is LoadingState.Error -> onLoadingError(state.error)
        }
    }

    private fun showWithBackground(color: Int) {
        setBackgroundColor(color)
        btn_retry.isVisible = false
        pb_loader.isVisible = true
        isVisible = true
    }

    private fun onLoadingError(error: Throwable) {
        btn_retry.isVisible = true
        pb_loader.isVisible = false
        isVisible = true
    }
}