package de.dojodev.rr457app.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["newsId", "mediaId"])
data class NewsMediaCrossRef(
    val newsId: Long,
    val mediaId: Long
)
