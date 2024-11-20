package de.dojodev.rr457app.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class NewsAndPreview(
    @Embedded
    val media: Media,
    @Relation(
        parentColumn = "mediaId",
        entityColumn = "newsId"
    )
    val news: News
)
