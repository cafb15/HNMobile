package com.macary.usecases

import com.macary.data.ResultWrapper
import com.macary.data.repository.NewsRepository
import com.macary.domain.News

/**
 * Created by Carlos Farfan on 6/01/2021.
 */
class DeleteNewsUseCase(private val repository: NewsRepository) {

    suspend fun invoke(news: News): ResultWrapper<String> = repository.deleteNews(news)
}