package com.macary.local.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.macary.local.BuildConfig

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
fun <T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> String.fromJson(default: T): T =
    if (this.isBlank()) {
        default
    } else {
        Gson().fromJson(this, object : TypeToken<T>() {}.type)
    }

fun ByteArray.toHex(): String {
    val result = StringBuilder()
    val hexChars = BuildConfig.HEX_CHARS.toCharArray()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(hexChars[firstIndex])
        result.append(hexChars[secondIndex])
    }
    return result.toString()
}