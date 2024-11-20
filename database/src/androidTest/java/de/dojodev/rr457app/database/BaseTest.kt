package de.dojodev.rr457app.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After

open class BaseTest {
    protected lateinit var context: Context
    protected lateinit var db: DB

    protected fun init() {
        context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, DB::class.java).build()
    }

    @After
    fun after() {
        this.db.close()
    }
}