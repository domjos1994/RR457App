package de.dojodev.rr457app.data.model

import de.dojodev.rr457app.database.model.Media as DBMedia
import de.dojodev.rr457app.rest.model.Media as RESTMedia

data class Media(
    var uid: Long,
    val title: String,
    val url: String
) {

    companion object {
        fun toDb(media: Media): DBMedia {
            return DBMedia(media.uid, media.title, media.url)
        }

        fun fromDB(dbMedia: DBMedia?): Media? {
            if(dbMedia != null) {
                return Media(dbMedia.mediaId, dbMedia.title, dbMedia.url)
            }
            return null
        }
        fun fromREST(restMedia: RESTMedia?): Media? {
            if(restMedia != null) {
                return Media(restMedia.uid.toLong(), restMedia.title, restMedia.publicUrl)
            }
            return null
        }
    }
}
