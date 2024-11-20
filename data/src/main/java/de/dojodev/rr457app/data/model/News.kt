package de.dojodev.rr457app.data.model

import de.dojodev.rr457app.database.model.NewsAndMedia
import de.dojodev.rr457app.database.model.NewsAndPreview
import de.dojodev.rr457app.database.model.NewsAndTag
import de.dojodev.rr457app.rest.model.News as RestNews
import de.dojodev.rr457app.database.model.News as DbNews

data class News(
    var uid: Long,
    val pid: String,
    val deleted: Boolean,
    val hidden: Boolean,
    val fullDay: Boolean,
    val isEvent: Boolean,
    val isTopNews: Boolean,
    val keywords: String,
    val location: String,
    val author: String,
    val authorEmail: String,
    val bodytext: String,
    val datetime: Long,
    val eventEnd: Long,
    val teaser: String,
    val title: String,
    val firstPreview: Media? = null,
    val media: List<Media>,
    val tags: List<Tag>,
    val state: State
) {

    companion object {
        fun toDB(news: News): DbNews {
            return DbNews(
                news.uid, news.pid, news.deleted, news.hidden, news.fullDay, news.isEvent,
                news.isTopNews, news.keywords, news.location, news.author, news.authorEmail,
                news.title, news.teaser, news.bodytext, news.datetime, news.eventEnd, news.firstPreview?.uid ?: 0L
            )
        }

        fun from(dbNews: DbNews? = null, newsMedia: NewsAndMedia? = null, newsTags: NewsAndTag? = null, newsPreview: NewsAndPreview? = null, restNews: RestNews? = null) : News? {
            if(dbNews == null && restNews == null) {
                return null
            }

            if(dbNews == null && restNews != null) {
                val media = mutableListOf<Media>()
                restNews.media.forEach {
                    media.add(Media.fromREST(it)!!)
                }
                val tags = mutableListOf<Tag>()
                restNews.tags.forEach {
                    tags.add(Tag.fromREST(it)!!)
                }

                return News(
                    restNews.uid, "${restNews.pid}", restNews.deleted, restNews.hidden,
                    restNews.fullDay, restNews.isEvent, restNews.istopnews,
                    restNews.keywords, restNews.locationSimple, restNews.author, restNews.authorEmail,
                    restNews.bodytext, restNews.datetime, restNews.eventEnd,
                    restNews.teaser, restNews.title,
                    Media.fromREST(restNews.firstPreview),
                    media,
                    tags,
                    State.New
                )
            }

            if(restNews == null) {
                val media = mutableListOf<Media>()
                newsMedia?.media?.forEach {
                    media.add(Media.fromDB(it)!!)
                }
                val tags = mutableListOf<Tag>()
                newsTags?.tags?.forEach {
                    tags.add(Tag.fromDB(it)!!)
                }

                return News(
                    dbNews?.newsId ?: 0L, dbNews?.pid ?: "",
                    dbNews?.deleted ?: false, dbNews?.hidden ?: false,
                    dbNews?.fullDay ?: false, dbNews?.isEvent ?: false,
                    dbNews?.isTopNews ?: false, dbNews?.keywords ?: "",
                    dbNews?.location ?: "", dbNews?.author ?: "",
                    dbNews?.email ?: "", dbNews?.bodytext ?: "",
                    dbNews?.datetime ?: 0L, dbNews?.eventEnd ?: 0L,
                    dbNews?.teaser ?: "", dbNews?.title ?: "",
                    Media.fromDB(newsPreview?.media),
                    media,
                    tags,
                    State.Deleted
                )
            } else {
                var state = State.Unchanged
                if( dbNews?.deleted != restNews.deleted ) {
                    state = State.Changed
                }
                if( dbNews?.hidden != restNews.hidden ) {
                    state = State.Changed
                }
                if(dbNews?.fullDay != restNews.fullDay ) {
                    state = State.Changed
                }
                if(dbNews?.isEvent != restNews.isEvent ) {
                    state = State.Changed
                }
                if(dbNews?.isTopNews != restNews.istopnews ) {
                    state = State.Changed
                }
                if(dbNews?.keywords != restNews.keywords ) {
                    state = State.Changed
                }
                if(dbNews?.location != restNews.locationSimple) {
                    state = State.Changed
                }
                if(dbNews?.bodytext != restNews.bodytext) {
                    state = State.Changed
                }
                if(dbNews?.title != restNews.title) {
                    state = State.Changed
                }
                if(dbNews?.teaser != restNews.teaser) {
                    state = State.Changed
                }
                if(dbNews?.datetime != restNews.datetime) {
                    state = State.Changed
                }
                if(dbNews?.eventEnd != restNews.eventEnd) {
                    state = State.Changed
                }
                if(newsPreview?.media?.title != restNews.firstPreview?.title || newsPreview?.media?.url != restNews.firstPreview?.publicUrl) {
                    state = State.Changed
                }
                newsMedia?.media?.forEach { media ->
                    val find = restNews.media.find { media.title==it.title && media.url == it.publicUrl }
                    if(find == null) {
                        state = State.Changed
                    }
                }
                newsTags?.tags?.forEach { tag ->
                    val find = restNews.tags.find { tag.title==it.title }
                    if(find == null) {
                        state = State.Changed
                    }
                }
                restNews.media.forEach { media ->
                    val find = newsMedia?.media?.find { media.title==it.title && media.publicUrl == it.url }
                    if(find == null) {
                        state = State.Changed
                    }
                }
                restNews.tags.forEach { tag ->
                    val find = newsTags?.tags?.find { tag.title==it.title }
                    if(find == null) {
                        state = State.Changed
                    }
                }
                if(state == State.Changed) {
                    val media = mutableListOf<Media>()
                    restNews.media.forEach {
                        media.add(Media.fromREST(it)!!)
                    }
                    val tags = mutableListOf<Tag>()
                    restNews.tags.forEach {
                        tags.add(Tag.fromREST(it)!!)
                    }

                    return News(
                        restNews.uid, "${restNews.pid}", restNews.deleted, restNews.hidden,
                        restNews.fullDay, restNews.isEvent, restNews.istopnews,
                        restNews.keywords, restNews.locationSimple, restNews.author, restNews.authorEmail,
                        restNews.bodytext, restNews.datetime, restNews.eventEnd,
                        restNews.teaser, restNews.title,
                        Media.fromREST(restNews.firstPreview),
                        media,
                        tags,
                        State.Changed
                    )
                } else {
                    val media = mutableListOf<Media>()
                    newsMedia?.media?.forEach {
                        media.add(Media.fromDB(it)!!)
                    }
                    val tags = mutableListOf<Tag>()
                    newsTags?.tags?.forEach {
                        tags.add(Tag.fromDB(it)!!)
                    }

                    return News(
                        dbNews?.newsId ?: 0L, dbNews?.pid ?: "",
                        dbNews?.deleted ?: false, dbNews?.hidden ?: false,
                        dbNews?.fullDay ?: false, dbNews?.isEvent ?: false,
                        dbNews?.isTopNews ?: false, dbNews?.keywords ?: "",
                        dbNews?.location ?: "", dbNews?.author ?: "",
                        dbNews?.email ?: "", dbNews?.bodytext ?: "",
                        dbNews?.datetime ?: 0L, dbNews?.eventEnd ?: 0L,
                        dbNews?.teaser ?: "", dbNews?.title ?: "",
                        Media.fromDB(newsPreview?.media),
                        media,
                        tags,
                        State.Unchanged
                    )
                }
            }
        }
    }
}
