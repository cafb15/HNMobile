package com.macary.usecases

import com.macary.data.ResultWrapper
import com.macary.data.repository.NewsRepository
import com.macary.data.utils.Constants
import com.macary.testshared.fakeNews
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
class DeleteNewsUseCaseTest {

    @Mock
    lateinit var repository: NewsRepository

    lateinit var deleteNewsCase: DeleteNewsUseCase

    @Before
    fun setup() {
        deleteNewsCase = DeleteNewsUseCase(repository)
    }

    @Test
    fun `invoke delete news repository`() = runBlocking {
        val result = ResultWrapper.Success(Constants.deleteSuccess)

        whenever(repository.deleteNews(fakeNews)).thenReturn(result)

        assertEquals(result, deleteNewsCase.invoke(fakeNews))
    }
}