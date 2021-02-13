package org.server.crud.ktor.configs

import io.ktor.config.*

fun environments(config: ApplicationConfig): Map<String, *> = mapOf(
    "mongodb" to MongoConfig(
        host = config.property("mongodb.host").getString(),
        port = config.property("mongodb.port").getString().toInt(),
        database = config.property("mongodb.database").getString(),
        username = config.property("mongodb.username").getString(),
        password = config.property("mongodb.password").getString(),
    )
)

data class MongoConfig(
    val host: String,
    val port: Int,
    val database: String,
    val username: String,
    val password: String
)
