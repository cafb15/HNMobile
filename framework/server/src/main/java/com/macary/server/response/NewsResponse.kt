package com.macary.server.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
@JsonClass(generateAdapter = true)
data class NewsResponse(
    val hits: List<NewsResponseData> = ArrayList()
)

@JsonClass(generateAdapter = true)
data class NewsResponseData(
    val title: String?,
    val url: String?,
    val author: String = "",
    @field:Json(name = "objectID") val objectId: String,
    @field:Json(name = "created_at") val createdAt: String = "",
    @field:Json(name = "comment_text") val commentText: String?,
    @field:Json(name = "story_title") val storyTitle: String?,
    @field:Json(name = "story_url") val storyUrl: String?
)