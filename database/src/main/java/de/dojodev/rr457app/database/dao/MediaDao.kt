package de.dojodev.rr457app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.dojodev.rr457app.database.model.Media
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @Query("SELECT * FROM Media")
    fun getAll(): Flow<List<Media>>

    @Insert
    fun insert(media: Media): Long

    @Update
    fun update(media: Media)

    @Delete
    fun delete(media: Media)
}