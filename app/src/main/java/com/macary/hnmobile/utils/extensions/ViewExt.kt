package com.macary.hnmobile.utils.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
fun ViewGroup.inflater(): LayoutInflater = LayoutInflater.from(context)

fun View.onClick(f: () -> Unit) {
    setOnClickListener { f() }
}

fun View.hide() {
    visibility = View.GONE
}