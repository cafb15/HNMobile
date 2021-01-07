package com.macary.server

import com.macary.server.response.NewsResponse
import retrofit2.http.GET

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
interface IApi {

    @GET(BuildConfig.GET_NEWS)
    suspend fun getNews(): NewsResponse
}