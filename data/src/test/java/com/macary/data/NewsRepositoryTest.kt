package com.macary.data

import com.macary.data.repository.NewsRepository
import com.macary.data.source.INewsDataSource
import com.macary.data.source.INewsDbDataSource
import com.macary.data.utils.Constants
import com.macary.testshared.fakeNews
import com.macary.testshared.fakeNewsList
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Carlos Farfan on 7/01/2021.
 */
@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {

    @Mock
    lateinit var dataSource: INewsDataSource

    @Mock
    lateinit var dbDataSource: INewsDbDataSource

    lateinit var repository: NewsRepository

    @Before
    fun setup() {
        repository = NewsRepository(dbDataSource, dataSource)
    }

    @Test
    fun `get news from db`() = runBlocking {
        val result = flowOf(fakeNewsList)

        whenever(dbDataSource.getAllNews()).thenReturn(result)

        assertEquals(result, repository.getAllNews())
    }

    @Test
    fun `update news`() = runBlocking {
        val result = ResultWrapper.Success(Constants.updateSuccess)

        whenever(dataSource.getAllNews()).thenReturn(ResultWrapper.Success(fakeNewsList))
        whenever(dbDataSource.getNewsById(any())).thenReturn(fakeNews)

        val response = repository.updateNews()

        verify(dbDataSource).saveAllNews(fakeNewsList)
        assertEquals(result, response)
    }

    @Test
    fun `delete news`() = runBlocking {
        val result = ResultWrapper.Success(Constants.deleteSuccess)

        whenever(dbDataSource.deleteNews(fakeNews)).thenReturn(result)

        assertEquals(result, repository.deleteNews(fakeNews))
    }
}