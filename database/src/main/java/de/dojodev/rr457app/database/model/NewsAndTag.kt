package de.dojodev.rr457app.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NewsAndTag(
    @Embedded
    val news: News,
    @Relation(
        parentColumn = "newsId",
        entityColumn = "tagId",
        associateBy = Junction(NewsTagCrossRef::class)
    )
    val tags: List<Tag>
)
