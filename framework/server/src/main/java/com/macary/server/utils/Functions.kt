package com.macary.server.utils

import com.macary.data.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
suspend fun <T> apiCall(dispatcher: CoroutineDispatcher, api: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(api.invoke())
        } catch (throwable: Throwable) {
            ResultWrapper.NetworkError(throwable)
        }
    }
}