package com.macary.server.di

import com.macary.data.source.INewsDataSource
import com.macary.server.IApi
import com.macary.server.source.NewsDataSource
import com.macary.server.utils.RestUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
private val networkModule = module {
    single(named("io")) { Dispatchers.IO }
    single<CoroutineDispatcher> { Dispatchers.Main }
    factory<IApi> { RestUtils.retrofit().create(IApi::class.java) }
}

private val sourceModule = module {
    factory<INewsDataSource> { NewsDataSource(get(), get(named("io"))) }
}

val serverModule = listOf(networkModule, sourceModule)