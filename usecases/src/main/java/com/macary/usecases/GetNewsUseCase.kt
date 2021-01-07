package com.macary.usecases

import com.macary.data.repository.NewsRepository
import com.macary.domain.News
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(private val repository: NewsRepository) {

    fun invoke(): Flow<List<News>> = repository.getAllNews()
}