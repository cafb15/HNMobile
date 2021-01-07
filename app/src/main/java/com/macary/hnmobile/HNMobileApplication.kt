package com.macary.hnmobile

import android.app.Application
import com.macary.data.di.dataModule
import com.macary.hnmobile.di.appModule
import com.macary.local.di.localModule
import com.macary.server.di.serverModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
class HNMobileApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HNMobileApplication)
            androidLogger()
            modules(appModule + dataModule + serverModule + localModule)
        }
    }
}