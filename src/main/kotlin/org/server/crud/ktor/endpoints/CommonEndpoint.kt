package org.server.crud.ktor.endpoints

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get

fun Route.hello() {

    get {
        call.respondText("Hello, world!", ContentType.Text.Html)
    }

}
