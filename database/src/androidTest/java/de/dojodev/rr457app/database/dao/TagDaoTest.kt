package de.dojodev.rr457app.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import de.dojodev.rr457app.database.BaseTest
import de.dojodev.rr457app.database.model.Tag
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TagDaoTest : BaseTest() {
    private lateinit var tagDao: TagDao

    @Before
    fun before() {
        super.init()
        this.tagDao = super.db.tagDao()
    }

    @Test
    fun testInserting() {
        runBlocking {
            var items = tagDao.getAll().first()
            assertEquals(0, items.size)

            val tag = Tag(1L, "Test")
            tag.tagId = tagDao.insert(tag)

            items = tagDao.getAll().first()
            assertEquals(1, items.size)

            tagDao.delete(tag)

            items = tagDao.getAll().first()
            assertEquals(0, items.size)
        }
    }

    @Test
    fun testUpdating() {
        runBlocking {
            var items = tagDao.getAll().first()
            assertEquals(0, items.size)

            val tag = Tag(1L, "Test")
            tag.tagId = tagDao.insert(tag)

            items = tagDao.getAll().first()
            assertEquals(1, items.size)

            tag.title = "Test 2"
            tagDao.update(tag)

            items = tagDao.getAll().first()
            assertEquals(1, items.size)
            assertEquals("Test 2", items[0].title)

            tagDao.delete(tag)

            items = tagDao.getAll().first()
            assertEquals(0, items.size)
        }
    }
}