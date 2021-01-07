package com.macary.hnmobile.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.macary.data.ResultWrapper
import com.macary.data.utils.Constants
import com.macary.hnmobile.CoroutineTestRule
import com.macary.hnmobile.ui.news.NewsViewModel
import com.macary.testshared.fakeNews
import com.macary.testshared.fakeNewsList
import com.macary.usecases.DeleteNewsUseCase
import com.macary.usecases.GetNewsUseCase
import com.macary.usecases.UpdateNewsUseCase
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var newsCase: GetNewsUseCase

    @Mock
    lateinit var updateNewsCase: UpdateNewsUseCase

    @Mock
    lateinit var deleteNewsCase: DeleteNewsUseCase

    @Mock
    lateinit var observer: Observer<NewsViewModel.UiModel>

    lateinit var viewModel: NewsViewModel

    @Before
    fun setup() {
        viewModel = NewsViewModel(newsCase, updateNewsCase, deleteNewsCase, coroutineTestRule.testDispatcher)
    }

    @Test
    fun `get news from db`() = runBlockingTest {
        val result = flowOf(fakeNewsList)

        whenever(newsCase.invoke()).thenReturn(result)

        viewModel.model.observeForever(observer)
        viewModel.getNews()

        verify(observer).onChanged(NewsViewModel.UiModel.Success(fakeNewsList))
    }

    @Test
    fun `update news`() = runBlockingTest {
        val result = Constants.updateSuccess

        whenever(updateNewsCase.invoke()).thenReturn(ResultWrapper.Success(result))

        viewModel.model.observeForever(observer)
        viewModel.updateNews()

        verify(observer).onChanged(NewsViewModel.UiModel.CrudSuccess(result))
    }

    @Test
    fun `delete news`() = runBlockingTest {
        val result = Constants.deleteSuccess

        whenever(deleteNewsCase.invoke(fakeNews)).thenReturn(ResultWrapper.Success(result))

        viewModel.model.observeForever(observer)
        viewModel.deleteNews(fakeNews)

        verify(observer).onChanged(NewsViewModel.UiModel.CrudSuccess(result))
    }
}