package org.server.crud.ktor.models

import org.bson.types.ObjectId

data class User(
        @Transient
        var id: ObjectId? = null,
        var name: String = "",
        var surname: String = ""
)
