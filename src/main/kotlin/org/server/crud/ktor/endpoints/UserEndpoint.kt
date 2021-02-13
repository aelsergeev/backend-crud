package org.server.crud.ktor.endpoints

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import org.bson.types.ObjectId
import org.server.crud.ktor.services.UserService

fun Route.user(userService: UserService) {
    route("/user") {
        get("/{id}") {
            val id = call.parameters["id"]

            if (id != null) call.respond(userService.getUserById(ObjectId(id)))
            else call.respond(HttpStatusCode.BadRequest)
        }

        get("/list") {
            call.respond(userService.getUsers())
        }

        put {
            call.respond(userService.createUser(call.receive()))
        }

        post {
            call.respond(userService.updateUser(call.receive()))
        }

        delete {
            call.respond(userService.deleteUser(call.receive()))
        }
    }
}
