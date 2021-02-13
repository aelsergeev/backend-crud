package org.server.crud.ktor.configs

import com.mongodb.*
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider

fun mongoClient(mongoConfig: MongoConfig): MongoClient {
    val pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
    )

    val credential = MongoCredential.createCredential(mongoConfig.username, mongoConfig.database, mongoConfig.password.toCharArray())
    val settings = MongoClientSettings
        .builder()
        .credential(credential)
        .codecRegistry(pojoCodecRegistry)
        .applyToClusterSettings { it.hosts(listOf(ServerAddress(mongoConfig.host, mongoConfig.port))) }
        .build()

    return MongoClients.create(settings)
}

fun mongoDatabase(mongoClient: MongoClient): MongoDatabase = mongoClient.getDatabase("admin")
