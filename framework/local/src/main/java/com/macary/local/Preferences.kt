package com.macary.local

import android.content.SharedPreferences
import androidx.core.content.edit
import com.macary.local.utils.fromJson
import com.macary.local.utils.toJson

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
class Preferences(private val preferences: SharedPreferences) {

    fun savedKeyCreate(storable: KeyStorable) =
        preferences.edit {
            putString(BuildConfig.PREFERENCES_KEY, storable.toJson())
            putBoolean(BuildConfig.PREFERENCES_KEY_CREATED, true)
            apply()
        }

    fun getKeyCreated(): KeyStorable = preferences.getString(BuildConfig.PREFERENCES_KEY, "")
        ?.fromJson(KeyStorable()) ?: KeyStorable()

    fun isKeyCreated(): Boolean = preferences.getBoolean(BuildConfig.PREFERENCES_KEY_CREATED, false)
}