package com.macary.data

import com.macary.data.utils.Constants
import com.squareup.moshi.JsonDataException
import java.lang.IllegalArgumentException
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
sealed class ResultWrapper<out T> {

    data class Success<out T>(val value: T) : ResultWrapper<T>()

    data class NetworkError(val throwable: Throwable): ResultWrapper<Nothing>(), Error {
        override val error
            get() = when (throwable) {
                is ConnectException -> Constants.connectError
                is SocketTimeoutException -> Constants.timeoutError
                is JsonDataException -> Constants.parserError
                is IllegalArgumentException -> Constants.parserError
                else -> Constants.unknownError
            }
    }

    data class DbError(val throwable: Throwable): ResultWrapper<Nothing>(), Error {
        override val error
            get() = when (throwable) {
                is JsonDataException -> Constants.parserError
                is IllegalArgumentException -> Constants.parserError
                else -> Constants.unknownError
            }
    }

    data class MessageError(override val error: String): ResultWrapper<Nothing>(), Error

    interface Error {
        val error: String
    }
}