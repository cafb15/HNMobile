package com.macary.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
@Entity(indices = [Index(value = ["title", "authorTime", "url"])])
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String = "",
    val authorTime: String = "",
    val url: String = "",
    var deleted: Boolean
)