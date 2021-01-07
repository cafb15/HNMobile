package com.macary.hnmobile.di

import com.macary.hnmobile.ui.news.NewsViewModel
import com.macary.hnmobile.ui.news.view.MainActivity
import com.macary.hnmobile.utils.networkMonitor.ConnectionStateMonitor
import com.macary.usecases.DeleteNewsUseCase
import com.macary.usecases.GetNewsUseCase
import com.macary.usecases.UpdateNewsUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
private val applicationModule = module {
    single { ConnectionStateMonitor(androidContext()) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        scoped { GetNewsUseCase(get()) }
        scoped { UpdateNewsUseCase(get()) }
        scoped { DeleteNewsUseCase(get()) }
        viewModel { NewsViewModel(get(), get(), get(), get()) }
    }
}

val appModule = listOf(applicationModule, scopesModule)