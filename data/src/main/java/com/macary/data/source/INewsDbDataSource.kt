package com.macary.data.source

import com.macary.data.ResultWrapper
import com.macary.domain.News
import kotlinx.coroutines.flow.Flow

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
interface INewsDbDataSource {

    suspend fun getNewsById(id: Int): News?

    suspend fun saveAllNews(news: List<News>)

    fun getAllNews(): Flow<List<News>>

    suspend fun deleteNews(news: News): ResultWrapper<String>
}