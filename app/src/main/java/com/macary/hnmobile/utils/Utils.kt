package com.macary.hnmobile.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
object Utils {

    fun <T> toJson(obj: T): String = Gson().toJson(obj)

    inline fun <reified T> fromJson(json: String?, default: T): T =
        if (json.isNullOrBlank()) {
            default
        } else {
            Gson().fromJson(json, object : TypeToken<T>() {}.type)
        }
}