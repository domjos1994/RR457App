package de.dojodev.rr457app.rest.model

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val uid: Long,
    val pid: Long,
    val deleted: Boolean,
    val hidden: Boolean,
    val fullDay: Boolean,
    val isEvent: Boolean,
    val istopnews: Boolean,
    val keywords: String,
    val locationSimple: String,
    val author: String,
    val authorEmail: String,
    val bodytext: String,
    val datetime: Long,
    val eventEnd: Long,
    val teaser: String,
    val title: String,
    val firstPreview: Media? = null,
    val media: List<Media>,
    val tags: List<Tag>
)
