package com.macary.usecases

import com.macary.data.ResultWrapper
import com.macary.data.repository.NewsRepository
import com.macary.data.utils.Constants
import com.nhaarman.mockitokotlin2.whenever
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
class UpdateNewsUseCaseTest {

    @Mock
    lateinit var repository: NewsRepository

    lateinit var updateNewsCase: UpdateNewsUseCase

    @Before
    fun setup() {
        updateNewsCase = UpdateNewsUseCase(repository)
    }

    @Test
    fun `invoke update news repository`() = runBlocking {
        val result = ResultWrapper.Success(Constants.updateSuccess)

        whenever(repository.updateNews()).thenReturn(result)

        assertEquals(result, updateNewsCase.invoke())
    }
}