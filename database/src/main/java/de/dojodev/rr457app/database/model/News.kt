package de.dojodev.rr457app.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("news")
data class News(
    @PrimaryKey
    var newsId: Long,
    val pid: String,
    val deleted: Boolean,
    val hidden: Boolean,
    val fullDay: Boolean,
    val isEvent: Boolean,
    val isTopNews: Boolean,
    val keywords: String,
    val location: String,
    val author: String,
    val email: String,
    var title: String,
    val teaser: String,
    val bodytext: String,
    val datetime: Long,
    val eventEnd: Long,
    val preview: Long
) {
    override fun toString(): String {
        return this.title
    }
}
