package de.dojodev.rr457app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import de.dojodev.rr457app.database.model.NewsAndMedia
import de.dojodev.rr457app.database.model.News
import de.dojodev.rr457app.database.model.NewsAndPreview
import de.dojodev.rr457app.database.model.NewsAndTag
import de.dojodev.rr457app.database.model.NewsMediaCrossRef
import de.dojodev.rr457app.database.model.NewsTagCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM News")
    fun getAll(): Flow<List<News>>

    @Insert
    fun insert(news: News): Long

    @Update
    fun update(news: News)

    @Delete
    fun delete(news: News)

    @Transaction
    @Query("SELECT * FROM media")
    fun getAllWithPreview(): Flow<List<NewsAndPreview>>

    @Transaction
    @Query("SELECT * FROM news")
    fun getAllWithMedia(): Flow<List<NewsAndMedia>>

    @Transaction
    @Query("SELECT * FROM news where newsId=:uid")
    fun getWithMedia(uid: Long): Flow<List<NewsAndMedia>>

    @Insert
    fun insertNewsMedia(newsMediaCrossRef: NewsMediaCrossRef)

    @Update
    fun updateNewsMedia(newsMediaCrossRef: NewsMediaCrossRef)

    @Delete
    fun deleteNewsMedia(newsMediaCrossRef: NewsMediaCrossRef)

    @Transaction
    @Query("DELETE FROM newsmediacrossref WHERE newsId=:uid")
    fun deleteMediaRefByUid(uid: Long)

    @Transaction
    @Query("SELECT * FROM news")
    fun getAllWithTag(): Flow<List<NewsAndTag>>

    @Transaction
    @Query("SELECT * FROM news where newsId=:uid")
    fun getWithTag(uid: Long): Flow<List<NewsAndTag>>

    @Insert
    fun insertNewsTag(newsTagCrossRef: NewsTagCrossRef)

    @Update
    fun updateNewsTag(newsTagCrossRef: NewsTagCrossRef)

    @Delete
    fun deleteNewsTag(newsTagCrossRef: NewsTagCrossRef)

    @Transaction
    @Query("DELETE FROM newstagcrossref WHERE newsId=:uid")
    fun deleteTagRefByUid(uid: Long)
}