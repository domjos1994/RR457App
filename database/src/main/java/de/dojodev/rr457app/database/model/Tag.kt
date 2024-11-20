package de.dojodev.rr457app.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("tags")
data class Tag(
    @PrimaryKey
    var tagId: Long,
    var title: String
) {

    override fun toString(): String {
        return this.title
    }
}
