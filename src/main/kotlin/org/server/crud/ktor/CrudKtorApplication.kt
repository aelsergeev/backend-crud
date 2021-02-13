package org.server.crud.ktor

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.server.crud.ktor.configs.MongoConfig
import org.server.crud.ktor.configs.environments
import org.server.crud.ktor.configs.mongoClient
import org.server.crud.ktor.configs.mongoDatabase
import org.server.crud.ktor.daos.UserDao
import org.server.crud.ktor.endpoints.hello
import org.server.crud.ktor.endpoints.user
import org.server.crud.ktor.services.UserService

fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

@Suppress("unused")
fun Application.module() {
    val environments = environments(environment.config)
    install(DefaultHeaders)
    install(Compression)
    install(CallLogging)
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    val mongoClient = mongoClient(environments["mongodb"] as MongoConfig)
    val mongoDatabase = mongoDatabase(mongoClient)
    val userDao = UserDao(mongoDatabase)
    val userService = UserService(userDao)

    routing {
        route("/api") {
            user(userService)
            hello()
        }
    }
}
