package com.macary.local.room

import androidx.room.*
import com.macary.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
@Dao
interface IDaoAccess {

    @Query("select * from NewsEntity where deleted = 0")
    fun getAllNews(): Flow<List<NewsEntity>>

    @Query("select * from NewsEntity where id = :id")
    suspend fun getNews(id: Int): NewsEntity?

    @Update
    suspend fun deleteNews(news: NewsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setAllNews(news: List<NewsEntity>)
}