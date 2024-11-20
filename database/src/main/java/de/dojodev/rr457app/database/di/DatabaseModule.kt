package de.dojodev.rr457app.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.dojodev.rr457app.database.DB
import de.dojodev.rr457app.database.dao.MediaDao
import de.dojodev.rr457app.database.dao.NewsDao
import de.dojodev.rr457app.database.dao.TagDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideTagDao(db: DB): TagDao {
        return db.tagDao()
    }

    @Provides
    fun provideMediaDao(db: DB): MediaDao {
        return db.mediaDao()
    }

    @Provides
    fun provideNewsDao(db: DB): NewsDao {
        return db.newsDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): DB {
        return Room.databaseBuilder(
            appContext,
            DB::class.java,
            "RR457"
        ).build()
    }
}