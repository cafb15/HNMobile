package com.macary.hnmobile.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macary.data.ResultWrapper
import com.macary.domain.News
import com.macary.hnmobile.utils.coroutines.ScopedViewModel
import com.macary.usecases.DeleteNewsUseCase
import com.macary.usecases.GetNewsUseCase
import com.macary.usecases.UpdateNewsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
class NewsViewModel(private val newsCase: GetNewsUseCase,
                    private val updateNewsCase: UpdateNewsUseCase,
                    private val deleteNewsCase: DeleteNewsUseCase,
                    uiDispatcher: CoroutineDispatcher)
    : ScopedViewModel(uiDispatcher) {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    sealed class UiModel {
        object Loading : UiModel()
        object NoLoading : UiModel()
        data class Success(val news: List<News>) : UiModel()
        data class CrudSuccess(val value: String) : UiModel()
        data class Error(val message: String) : UiModel()
    }

    fun getNews() {
        launch {
            newsCase.invoke().collect { _model.value = UiModel.Success(it) }
        }
    }

    fun updateNews() {
        launch {
            _model.value = UiModel.Loading

            when (val result = updateNewsCase.invoke()) {
                is ResultWrapper.Success -> _model.value = UiModel.CrudSuccess(result.value)
                is ResultWrapper.Error -> _model.value = UiModel.Error(result.error)
            }

            _model.value = UiModel.NoLoading
        }
    }

    fun deleteNews(news: News) {
        launch {
            news.deleted = true

            when (val result = deleteNewsCase.invoke(news)) {
                is ResultWrapper.Success -> _model.value = UiModel.CrudSuccess(result.value)
                is ResultWrapper.Error -> _model.value = UiModel.Error(result.error)
            }
        }
    }
}