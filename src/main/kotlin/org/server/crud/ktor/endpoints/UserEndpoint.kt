package org.server.crud.ktor.endpoints

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import org.bson.types.ObjectId
import org.server.crud.ktor.service.UserService

fun Route.user() {
    val userService = UserService()

    get("/user/{id}") {
        val id = call.parameters["id"]

        if (id != null) call.respond(userService.getUserById(ObjectId(id)))
        else call.respond(HttpStatusCode.BadRequest)
    }

    get("/user/list") {
        call.respond(userService.getUsers())
    }
}
