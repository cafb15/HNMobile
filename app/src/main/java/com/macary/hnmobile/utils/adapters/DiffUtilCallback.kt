package com.macary.hnmobile.utils.adapters

import androidx.recyclerview.widget.DiffUtil

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
class DiffUtilCallback<ITEM>(private val oldItems: List<ITEM>, private val newItems: List<ITEM>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}