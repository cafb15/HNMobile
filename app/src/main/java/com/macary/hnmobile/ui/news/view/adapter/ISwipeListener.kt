package com.macary.hnmobile.ui.news.view.adapter

import com.macary.hnmobile.utils.adapters.AbstractAdapter

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
interface ISwipeListener {

    fun onSwiped(holder: AbstractAdapter.Holder, direction: Int, position: Int)
}