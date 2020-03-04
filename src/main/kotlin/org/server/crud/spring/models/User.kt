package org.server.crud.spring.models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        @Id
        var id: String? = null,
        var name: String = "",
        var surname: String = ""
) {
        constructor(userCreateRequest: UserCreateRequest) : this() {
                name = userCreateRequest.name
                surname = userCreateRequest.surname
        }
        constructor(userUpdateRequest: UserUpdateRequest) : this() {
                id = userUpdateRequest.id
                name = userUpdateRequest.name
                surname = userUpdateRequest.surname
        }
        constructor(userDeleteRequest: UserDeleteRequest) : this() {
                id = userDeleteRequest.id
        }
}

data class UserCreateRequest(var name: String, var surname: String)

data class UserUpdateRequest(var id: String, var name: String, var surname: String)

data class UserDeleteRequest(var id: String)
