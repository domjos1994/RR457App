package de.dojodev.rr457app.database.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import de.dojodev.rr457app.database.BaseTest
import de.dojodev.rr457app.database.model.Media
import de.dojodev.rr457app.database.model.News
import de.dojodev.rr457app.database.model.NewsMediaCrossRef
import de.dojodev.rr457app.database.model.NewsTagCrossRef
import de.dojodev.rr457app.database.model.Tag
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewsDaoTest : BaseTest() {
    private lateinit var newsDao: NewsDao
    private lateinit var mediaDao: MediaDao
    private lateinit var tagDao: TagDao

    @Before
    fun before() {
        super.init()
        this.newsDao = super.db.newsDao()
        this.mediaDao = super.db.mediaDao()
        this.tagDao = super.db.tagDao()
    }

    @Test
    fun testInserting() {
        runBlocking {
            var items = newsDao.getAll().first()
            assertEquals(0, items.size)

            val news = createNewsObject()
            news.newsId = newsDao.insert(news)

            items = newsDao.getAll().first()
            assertEquals(1, items.size)

            newsDao.delete(news)

            items = newsDao.getAll().first()
            assertEquals(0, items.size)
        }
    }

    @Test
    fun testUpdating() {
        runBlocking {
            var items = newsDao.getAll().first()
            assertEquals(0, items.size)

            val news = createNewsObject()
            news.newsId = newsDao.insert(news)

            items = newsDao.getAll().first()
            assertEquals(1, items.size)

            news.title = "Test 2"
            newsDao.update(news)

            items = newsDao.getAll().first()
            assertEquals(1, items.size)
            assertEquals("Test 2", items[0].title)

            newsDao.delete(news)

            items = newsDao.getAll().first()
            assertEquals(0, items.size)
        }
    }

    @Test
    fun testNewsAndTag() {
        runBlocking {
            val news = createNewsObject()
            news.newsId = newsDao.insert(news)

            val tag = Tag(1L, "Tag")
            tag.tagId = tagDao.insert(tag)

            val crossRef = NewsTagCrossRef(news.newsId, tag.tagId)
            newsDao.insertNewsTag(crossRef)

            var items = newsDao.getAllWithTag().first()
            assertNotEquals(0, items.size)
            assertNotEquals(0, items[0].tags.size)

            newsDao.deleteNewsTag(crossRef)

            items = newsDao.getAllWithTag().first()
            assertNotEquals(0, items.size)
            assertEquals(0, items[0].tags.size)

            tagDao.delete(tag)
            newsDao.delete(news)

        }
    }

    @Test
    fun testNewsAndMedia() {
        runBlocking {
            val news = createNewsObject()
            news.newsId = newsDao.insert(news)

            val media = Media(1L, "Tag", "")
            media.mediaId = mediaDao.insert(media)

            val crossRef = NewsMediaCrossRef(news.newsId, media.mediaId)
            newsDao.insertNewsMedia(crossRef)

            var items = newsDao.getAllWithMedia().first()
            assertNotEquals(0, items.size)
            assertNotEquals(0, items[0].media.size)

            newsDao.deleteNewsMedia(crossRef)

            items = newsDao.getAllWithMedia().first()
            assertNotEquals(0, items.size)
            assertEquals(0, items[0].media.size)

            mediaDao.delete(media)
            newsDao.delete(news)
        }
    }

    private fun createNewsObject(): News {
        return News(
            1L, "",
            deleted = false,
            hidden = false,
            fullDay = false,
            isEvent = false,
            isTopNews = false,
            keywords = "",
            location = "",
            author = "",
            email = "",
            title = "Test",
            teaser = "",
            bodytext = "",
            datetime = 0L,
            eventEnd = 0L,
            preview = 0L
        )
    }
}