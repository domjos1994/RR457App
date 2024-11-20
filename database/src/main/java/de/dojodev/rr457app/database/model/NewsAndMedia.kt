package de.dojodev.rr457app.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NewsAndMedia(
    @Embedded
    val news: News,
    @Relation(
        parentColumn = "newsId",
        entityColumn = "mediaId",
        associateBy = Junction(NewsMediaCrossRef::class)
    )
    val media: List<Media>
)
