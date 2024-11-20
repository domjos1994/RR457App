package de.dojodev.rr457app.data.repositories

import de.dojodev.rr457app.data.model.Media
import de.dojodev.rr457app.data.model.News
import de.dojodev.rr457app.data.model.State
import de.dojodev.rr457app.data.model.Tag
import de.dojodev.rr457app.database.dao.MediaDao
import de.dojodev.rr457app.database.dao.NewsDao
import de.dojodev.rr457app.database.dao.TagDao
import de.dojodev.rr457app.database.model.NewsMediaCrossRef
import de.dojodev.rr457app.database.model.NewsTagCrossRef
import de.dojodev.rr457app.rest.requests.NewsRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface NewsRepository {

    suspend fun load(): Flow<List<News>>
}

class DefaultNewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val tagDao: TagDao,
    private val mediaDao: MediaDao
) : NewsRepository {


    override suspend fun load(): Flow<List<News>> {
        return flow {
            val request = NewsRequest()
            val rNews = request.getNews().first()

            val dNews = newsDao.getAll().first()
            val tNews = newsDao.getAllWithTag().first()
            val mNews = newsDao.getAllWithMedia().first()
            val pNews = newsDao.getAllWithPreview().first()

            val items = mutableListOf<News>()
            rNews.forEach { news ->
                val d = dNews.find { news.uid == it.newsId }
                val t = tNews.find { news.uid == it.news.newsId }
                val m = mNews.find { news.uid == it.news.newsId }
                val p = pNews.find{ news.uid == it.news.newsId }

                News.from(d, m, t, p, news)?.let { items.add(it) }
            }
            dNews.forEach { news ->
                val r = rNews.find { news.newsId == it.uid }
                val t = tNews.find { news.newsId == it.news.newsId }
                val m = mNews.find { news.newsId == it.news.newsId }
                val p = pNews.find{ news.newsId == it.news.newsId }

                News.from(news, m, t, p, r)?.let { items.add(it) }
            }

            val media = mediaDao.getAll().first()
            val tags = tagDao.getAll().first()
            items.filter { item -> item.state == State.Deleted || item.state == State.Changed }.forEach { item ->
                newsDao.deleteTagRefByUid(item.uid)
                newsDao.deleteMediaRefByUid(item.uid)
                newsDao.delete(News.toDB(item))
            }
            items.filter { item -> item.state == State.New || item.state == State.Changed }.forEach { item ->
                if(item.firstPreview != null) {
                    val find = media.find { it.mediaId == item.firstPreview.uid }
                    if(find != null) {
                        mediaDao.update(Media.toDb(item.firstPreview))
                    } else {
                        item.firstPreview.uid = mediaDao.insert(Media.toDb(item.firstPreview))
                    }
                }
                item.uid = newsDao.insert(News.toDB(item))

                item.media.forEach { m ->
                    val find = media.find { it.mediaId == m.uid }
                    if(find != null) {
                        mediaDao.update(Media.toDb(m))
                    } else {
                        m.uid = mediaDao.insert(Media.toDb(m))
                    }
                    val ref = NewsMediaCrossRef(item.uid, m.uid)
                    newsDao.insertNewsMedia(ref)
                }


                item.tags.forEach { m ->
                    val find = tags.find { it.tagId == m.uid }
                    if(find != null) {
                        tagDao.update(Tag.toDB(m))
                    } else {
                        m.uid = tagDao.insert(Tag.toDB(m))
                    }
                    val ref = NewsTagCrossRef(item.uid, m.uid)
                    newsDao.insertNewsTag(ref)
                }
            }

            emit(items)
        }
    }

}