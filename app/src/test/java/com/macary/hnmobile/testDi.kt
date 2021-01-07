package com.macary.hnmobile

import com.macary.data.ResultWrapper
import com.macary.data.di.dataModule
import com.macary.data.source.INewsDataSource
import com.macary.data.source.INewsDbDataSource
import com.macary.data.utils.Constants
import com.macary.domain.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Carlos Farfan on 7/01/2021.
 */

fun initMockedDi(vararg testModules: Module) {
    startKoin {
        modules(listOf(mockedModule, dataModule) + testModules)
    }
}

private val mockedModule = module {
    single<INewsDataSource> { FakeNewsDataSource() }
    single<INewsDbDataSource> { FakeNewsDbDataSource() }
    single { Dispatchers.Unconfined }
}

class FakeNewsDataSource : INewsDataSource {

    override suspend fun getAllNews(): ResultWrapper<List<News>> =
        ResultWrapper.Success(fakeNewsList)
}

class FakeNewsDbDataSource : INewsDbDataSource {

    override suspend fun getNewsById(id: Int): News? =
        if (id == 1) {
            News()
        } else {
            null
        }

    override suspend fun saveAllNews(news: List<News>) {}

    override fun getAllNews(): Flow<List<News>> = flowOf(fakeNewsList)

    override suspend fun deleteNews(news: News): ResultWrapper<String> =
        ResultWrapper.Success(Constants.deleteSuccess)
}