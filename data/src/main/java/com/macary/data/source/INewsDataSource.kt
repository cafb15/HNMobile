package com.macary.data.source

import com.macary.data.ResultWrapper
import com.macary.domain.News

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
interface INewsDataSource {

    suspend fun getAllNews(): ResultWrapper<List<News>>
}