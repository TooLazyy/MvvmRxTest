package com.example.mvvmrxtest.features.restaurants.controller

import android.view.ViewGroup
import com.example.domain.RestaurantInfo
import com.example.mvvmrxtest.R
import kotlinx.android.synthetic.main.item_restaurant.view.*
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class RestaurantController(
    private val onItemClickListener: (RestaurantInfo) -> Unit
) : BindableItemController<RestaurantInfo, RestaurantController.Holder>() {

    override fun getItemId(item: RestaurantInfo): String = item.id

    override fun createViewHolder(parent: ViewGroup?): Holder =
        Holder(parent)

    inner class Holder(parent: ViewGroup?) : BindableViewHolder<RestaurantInfo>(parent, R.layout.item_restaurant) {

        override fun bind(item: RestaurantInfo) {
            with(itemView) {
                iv_logo.setImageResource(R.mipmap.ic_launcher)
                tv_name.text = item.name
                tv_description.text = item.description
                setOnClickListener {
                    onItemClickListener(item)
                }
            }
        }
    }
}