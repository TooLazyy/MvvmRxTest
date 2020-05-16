package com.example.mvvmrxtest.core.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val DEFAULT_LOAD_MORE_THRESHOLD = 10

fun RecyclerView.onLoadMoreListener(
    threshold: Int = DEFAULT_LOAD_MORE_THRESHOLD,
    onLoadMore: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val totalItems = recyclerView.adapter?.itemCount ?: 0
            val lastVisible =
                (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (totalItems - lastVisible <= threshold) {
                onLoadMore()
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {}
    })
}
