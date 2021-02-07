package org.server.crud.ktor.configs

import com.mongodb.*
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider

fun mongoClient(): MongoClient {
    val pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
    )

    val credential = MongoCredential.createCredential("root", "admin", "example".toCharArray())
    val settings = MongoClientSettings
        .builder()
        .credential(credential)
        .codecRegistry(pojoCodecRegistry)
        .applyToClusterSettings { it.hosts(listOf(ServerAddress("localhost", 27017))) }
        .build()

    return MongoClients.create(settings)
}
