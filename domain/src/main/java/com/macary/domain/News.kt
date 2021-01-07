package com.macary.domain

data class News(
    val id: Int = 0,
    val title: String = "",
    val authorTime: String = "",
    val url: String = "",
    var deleted: Boolean = false
)