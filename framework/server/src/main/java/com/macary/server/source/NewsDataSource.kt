package com.macary.server.source

import com.macary.data.ResultWrapper
import com.macary.data.source.INewsDataSource
import com.macary.domain.News
import com.macary.server.IApi
import com.macary.server.utils.apiCall
import com.macary.server.utils.toDomainNews
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
class NewsDataSource(private val api: IApi, private val dispatcher: CoroutineDispatcher)
    : INewsDataSource {

    override suspend fun getAllNews(): ResultWrapper<List<News>> =
        apiCall(dispatcher) { api.getNews().toDomainNews() }
}