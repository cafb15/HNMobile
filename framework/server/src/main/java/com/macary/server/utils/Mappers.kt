package com.macary.server.utils

import com.macary.domain.News
import com.macary.server.response.NewsResponse
import com.macary.server.response.NewsResponseData
import java.util.*

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
fun NewsResponse.toDomainNews() = hits.map { it.toDomainNews() }

fun NewsResponseData.toDomainNews(): News {
    val createdAt = createdAt.toDate("yyyy-MM-dd'T'hh:mm:ss") ?: Date()

    return News(
        objectId.toInt(),
            storyTitle ?: title ?: "",
            "$author - ${createdAt.difference()}",
            storyUrl ?: url ?: "",
            false
    )
}