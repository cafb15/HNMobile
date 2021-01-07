package com.macary.data.repository

import com.macary.data.ResultWrapper
import com.macary.data.source.INewsDataSource
import com.macary.data.source.INewsDbDataSource
import com.macary.data.utils.Constants
import com.macary.domain.News
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
class NewsRepository(private val dbDataSource: INewsDbDataSource,
                     private val dataSource: INewsDataSource) {

    fun getAllNews(): Flow<List<News>> = dbDataSource.getAllNews()

    suspend fun updateNews(): ResultWrapper<String> =
        when (val result = dataSource.getAllNews()) {
            is ResultWrapper.Success -> {
                val values = result.value
                values.forEach {
                    val news = dbDataSource.getNewsById(it.id)
                    it.deleted = news?.deleted ?: false
                }

                if (values.isNotEmpty()) {
                    dbDataSource.saveAllNews(result.value)
                }

                ResultWrapper.Success(Constants.updateSuccess)
            }
            else -> ResultWrapper.MessageError((result as ResultWrapper.Error).error)
        }

    suspend fun deleteNews(news: News): ResultWrapper<String> = dbDataSource.deleteNews(news)
}