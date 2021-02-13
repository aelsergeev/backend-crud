package org.server.crud.ktor.models

import org.bson.types.ObjectId

data class User(
    @Transient
    var id: ObjectId? = null,
    var name: String = "",
    var surname: String = ""
) {
    constructor(userCreateRequest: UserCreateRequest) : this() {
        name = userCreateRequest.name
        surname = userCreateRequest.surname
    }

    constructor(userUpdateRequest: UserUpdateRequest) : this() {
        id = ObjectId(userUpdateRequest.id)
        name = userUpdateRequest.name
        surname = userUpdateRequest.surname
    }

    constructor(userDeleteRequest: UserDeleteRequest) : this() {
        id = ObjectId(userDeleteRequest.id)
    }
}

data class UserCreateRequest(var name: String, var surname: String)

data class UserUpdateRequest(var id: String, var name: String, var surname: String)

data class UserDeleteRequest(var id: String)
