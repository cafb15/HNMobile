package com.macary.local.utils

import com.macary.domain.News
import com.macary.local.entity.NewsEntity

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
fun NewsEntity.toDomainNews(): News = News(id, title, authorTime, url, deleted)

fun News.toRoomNews(): NewsEntity = NewsEntity(id, title, authorTime, url, deleted)