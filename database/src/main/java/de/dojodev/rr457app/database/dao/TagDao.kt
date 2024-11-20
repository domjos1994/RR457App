package de.dojodev.rr457app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import de.dojodev.rr457app.database.model.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tags")
    fun getAll(): Flow<List<Tag>>

    @Insert
    fun insert(tag: Tag): Long

    @Update
    fun update(tag: Tag)

    @Delete
    fun delete(tag: Tag)
}