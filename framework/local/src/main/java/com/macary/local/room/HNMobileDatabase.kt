package com.macary.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.macary.local.BuildConfig
import com.macary.local.entity.NewsEntity
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
@Database(entities = [NewsEntity::class], version = 1)
abstract class HNMobileDatabase : RoomDatabase() {

    abstract fun daoAccess(): IDaoAccess

    companion object {

        fun build(context: Context, dbKey: String): HNMobileDatabase {
            val supportFactory = SupportFactory(SQLiteDatabase.getBytes(dbKey.toCharArray()))

            return Room.databaseBuilder(context, HNMobileDatabase::class.java, BuildConfig.DB_NAME)
                .openHelperFactory(supportFactory)
                .build()
        }
    }
}