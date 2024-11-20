package de.dojodev.rr457app.data.model

import de.dojodev.rr457app.database.model.Tag as DBTag
import de.dojodev.rr457app.rest.model.Tag as RESTTag

data class Tag(
    var uid: Long,
    val title: String
) {
    companion object {
        fun toDB(tag: Tag): DBTag {
            return DBTag(tag.uid, tag.title)
        }
        fun fromDB(dbTag: DBTag?): Tag? {
            if(dbTag != null) {
                return Tag(dbTag.tagId, dbTag.title)
            }
            return null
        }
        fun fromREST(restTag: RESTTag?): Tag? {
            if(restTag != null) {
                return Tag(restTag.uid.toLong(), restTag.title)
            }
            return null
        }
    }
}
