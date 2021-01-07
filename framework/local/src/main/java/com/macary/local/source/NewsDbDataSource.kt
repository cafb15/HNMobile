package com.macary.local.source

import com.macary.data.ResultWrapper
import com.macary.data.source.INewsDbDataSource
import com.macary.data.utils.Constants
import com.macary.domain.News
import com.macary.local.room.HNMobileDatabase
import com.macary.local.utils.roomTask
import com.macary.local.utils.toDomainNews
import com.macary.local.utils.toRoomNews
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
class NewsDbDataSource(db: HNMobileDatabase, private val dispatcher: CoroutineDispatcher)
    : INewsDbDataSource {

    private val dao = db.daoAccess()

    override suspend fun getNewsById(id: Int): News? = dao.getNews(id)?.toDomainNews()

    override suspend fun saveAllNews(news: List<News>) = dao.setAllNews(news.map { it.toRoomNews() })

    override fun getAllNews(): Flow<List<News>> =
        dao.getAllNews().map { news -> news.map { it.toDomainNews() } }

    override suspend fun deleteNews(news: News): ResultWrapper<String> =
            roomTask(dispatcher) {
                dao.deleteNews(news.toRoomNews())
                Constants.deleteSuccess
            }
}