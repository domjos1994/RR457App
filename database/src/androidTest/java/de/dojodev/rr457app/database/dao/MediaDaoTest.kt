package de.dojodev.rr457app.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import de.dojodev.rr457app.database.BaseTest
import de.dojodev.rr457app.database.model.Media
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MediaDaoTest : BaseTest() {
    private lateinit var mediaDao: MediaDao

    @Before
    fun before() {
        super.init()
        this.mediaDao = super.db.mediaDao()
    }

    @Test
    fun testInserting() {
        runBlocking {
            var items = mediaDao.getAll().first()
            assertEquals(0, items.size)

            val media = Media(1L, "Test", "https://test.de")
            media.mediaId = mediaDao.insert(media)

            items = mediaDao.getAll().first()
            assertEquals(1, items.size)

            mediaDao.delete(media)

            items = mediaDao.getAll().first()
            assertEquals(0, items.size)
        }
    }

    @Test
    fun testUpdating() {
        runBlocking {
            var items = mediaDao.getAll().first()
            assertEquals(0, items.size)

            val media = Media(1L, "Test", "https://test.de")
            media.mediaId = mediaDao.insert(media)

            items = mediaDao.getAll().first()
            assertEquals(1, items.size)

            media.title = "Test 2"
            mediaDao.update(media)

            items = mediaDao.getAll().first()
            assertEquals(1, items.size)
            assertEquals("Test 2", items[0].title)

            mediaDao.delete(media)

            items = mediaDao.getAll().first()
            assertEquals(0, items.size)
        }
    }
}