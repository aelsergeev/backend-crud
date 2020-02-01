package org.server.crud.ktor.dao

import com.mongodb.BasicDBObject
import org.bson.types.ObjectId
import org.server.crud.ktor.configs.mongoClient
import org.server.crud.ktor.models.User

class UserDao {
    private val mongoDatabase = mongoClient().getDatabase("crud")
    private val mongoCollection = mongoDatabase.getCollection("user", User::class.java)

    fun getUserById(id: ObjectId): User {
        return mongoCollection.find(BasicDBObject("_id", id)).first() ?: throw IllegalArgumentException("User not found $id")
    }

    fun getUserList(): List<User> {
        return mongoCollection.find().toList()
    }
}
