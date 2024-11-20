package de.dojodev.rr457app.rest.model

import kotlinx.serialization.Serializable

@Serializable
data class Media(val uid: Int, val title: String, val publicUrl: String)