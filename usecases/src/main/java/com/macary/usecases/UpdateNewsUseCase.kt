package com.macary.usecases

import com.macary.data.ResultWrapper
import com.macary.data.repository.NewsRepository

/**
 * Created by Carlos Farfan on 6/01/2021.
 */
class UpdateNewsUseCase(private val repository: NewsRepository) {

    suspend fun invoke(): ResultWrapper<String> = repository.updateNews()
}