package com.macary.usecases

import com.macary.data.repository.NewsRepository
import com.macary.testshared.fakeNewsList
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
class GetNewsUseCaseTest {

    @Mock
    lateinit var repository: NewsRepository

    lateinit var getNewsCase: GetNewsUseCase

    @Before
    fun setup() {
        getNewsCase = GetNewsUseCase(repository)
    }

    @Test
    fun `invoke call get news repository`() = runBlocking {
        val result = flowOf(fakeNewsList)

        whenever(repository.getAllNews()).thenReturn(result)

        assertEquals(result, getNewsCase.invoke())
    }
}