package org.server.crud.ktor.configs

import com.mongodb.*
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider

fun mongoClient(): MongoClient {
    val pojoCodecRegistry = fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build())
    )

    val server = ServerAddress("localhost", 27017)
    val credential = MongoCredential.createCredential("root", "admin", "example".toCharArray())
    val options = MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build()

    return MongoClient(server, credential, options)
}
