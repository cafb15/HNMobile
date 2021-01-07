package com.macary.local.utils

import com.macary.data.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
suspend fun <T> roomTask(dispatcher: CoroutineDispatcher, task: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(task.invoke())
        } catch (throwable: Throwable) {
            ResultWrapper.DbError(throwable)
        }
    }
}