package com.macary.data.di

import com.macary.data.repository.NewsRepository
import org.koin.dsl.module

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
val dataModule = module {
    factory { NewsRepository(get(), get()) }
}