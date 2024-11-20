package de.dojodev.rr457app.database

import androidx.room.Database
import androidx.room.RoomDatabase
import de.dojodev.rr457app.database.dao.MediaDao
import de.dojodev.rr457app.database.dao.NewsDao
import de.dojodev.rr457app.database.dao.TagDao
import de.dojodev.rr457app.database.model.Media
import de.dojodev.rr457app.database.model.News
import de.dojodev.rr457app.database.model.NewsMediaCrossRef
import de.dojodev.rr457app.database.model.NewsTagCrossRef
import de.dojodev.rr457app.database.model.Tag

@Database(
    entities = [
        News::class, Tag::class, Media::class,
        NewsMediaCrossRef::class, NewsTagCrossRef::class
    ],
    version = 1
)
abstract class DB : RoomDatabase() {

    abstract fun mediaDao(): MediaDao
    abstract fun tagDao(): TagDao
    abstract fun newsDao(): NewsDao
}