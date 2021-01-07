package com.macary.hnmobile.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.macary.data.utils.Constants
import com.macary.hnmobile.initMockedDi
import com.macary.hnmobile.ui.news.NewsViewModel
import com.macary.testshared.fakeNews
import com.macary.testshared.fakeNewsList
import com.macary.usecases.DeleteNewsUseCase
import com.macary.usecases.GetNewsUseCase
import com.macary.usecases.UpdateNewsUseCase
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Carlos Farfan on 7/01/2021.
 */
@RunWith(MockitoJUnitRunner::class)
class NewsIntegrationTest : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<NewsViewModel.UiModel>

    lateinit var viewModel: NewsViewModel

    @Before
    fun setup() {
        val module = module {
            factory { NewsViewModel(get(), get(), get(), get()) }
            factory { GetNewsUseCase(get()) }
            factory { UpdateNewsUseCase(get()) }
            factory { DeleteNewsUseCase(get()) }
        }

        initMockedDi(module)
        viewModel = get()
    }

    @Test
    fun `get news from db`() {
        viewModel.model.observeForever(observer)
        viewModel.getNews()

        verify(observer).onChanged(NewsViewModel.UiModel.Success(fakeNewsList))
    }

    @Test
    fun `update news`() {
        viewModel.model.observeForever(observer)
        viewModel.updateNews()

        verify(observer).onChanged(NewsViewModel.UiModel.CrudSuccess(Constants.updateSuccess))
    }

    @Test
    fun `delete news`() {
        viewModel.model.observeForever(observer)
        viewModel.deleteNews(fakeNews)

        verify(observer).onChanged(NewsViewModel.UiModel.CrudSuccess(Constants.deleteSuccess))
    }
}