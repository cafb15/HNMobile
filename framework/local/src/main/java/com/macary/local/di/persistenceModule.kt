package com.macary.local.di

import androidx.security.crypto.EncryptedSharedPreferences
import com.macary.data.source.INewsDbDataSource
import com.macary.local.BuildConfig
import com.macary.local.Preferences
import com.macary.local.room.HNMobileDatabase
import com.macary.local.source.NewsDbDataSource
import com.macary.local.utils.KeyUtils
import com.macary.local.utils.toHex
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
private val persistenceModule = module {
    single { KeyUtils.generateMasterKey(androidContext()) }
    single {
        Preferences(EncryptedSharedPreferences.create(androidContext(), BuildConfig.PREFERENCES_NAME, get(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM))
    }

    fun keyDbCreated(preferences: Preferences): String {
        if (!preferences.isKeyCreated()) {
            val rawDbKey = KeyUtils.generateRawDbKey()
            val storable = KeyUtils.toKeyStorable(rawDbKey)

            preferences.savedKeyCreate(storable)
        }

        val rawDbKey = KeyUtils.getRawDbKey(preferences.getKeyCreated())
        return rawDbKey.toHex()
    }

    single(named("dbKey")) { keyDbCreated(get()) }
    single { HNMobileDatabase.build(androidContext(), get(named("dbKey"))) }
}

private val sourceDbModule = module {
    factory<INewsDbDataSource> { NewsDbDataSource(get(), get(named("io"))) }
}

val localModule = listOf(persistenceModule, sourceDbModule)