package de.dojodev.rr457app.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("media")
data class Media(
    @PrimaryKey
    var mediaId: Long,
    var title: String,
    val url: String
) {
    override fun toString(): String {
        return this.title
    }
}
