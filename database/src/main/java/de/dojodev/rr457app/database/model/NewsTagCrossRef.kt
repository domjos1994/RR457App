package de.dojodev.rr457app.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["newsId", "tagId"])
data class NewsTagCrossRef(
    val newsId: Long,
    val tagId: Long
)
