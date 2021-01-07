package com.macary.server.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
fun String.toDate(pattern: String): Date? = SimpleDateFormat(pattern, Locale.getDefault()).parse(this)

fun Date.difference(): String {
    val currentDate = Calendar.getInstance().timeInMillis

    val diff: Long = time - currentDate
    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    when {
        days > 0 -> {
            return if (days == 1L) {
                "Yesterday"
            } else {
                "$days days"
            }
        }
        hours > 0 -> {
            return "${hours}h"
        }
        minutes > 0 -> {
            return "${minutes}m"
        }
        seconds > 0 -> {
            return "${seconds}s"
        }
        else -> return ""
    }
}