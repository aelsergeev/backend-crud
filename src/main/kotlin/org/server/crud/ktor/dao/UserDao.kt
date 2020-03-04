package org.server.crud.ktor.dao

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.combine
import com.mongodb.client.model.Updates.set
import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.server.crud.ktor.configs.mongoClient
import org.server.crud.ktor.models.User

class UserDao {
    private val mongoDatabase = mongoClient().getDatabase("admin")
    private val mongoCollection = mongoDatabase.getCollection("user", User::class.java)

    fun getUserById(id: ObjectId): User {
        return mongoCollection.find(eq("_id", id)).first() ?: throw IllegalArgumentException("User not found $id")
    }

    fun getUserList(): List<User> {
        return mongoCollection.find().toList()
    }

    fun createUser(user: User): User {
        mongoCollection.insertOne(user)
        return user
    }

    fun updateUser(user: User): User {
        val filter = eq("_id", user.id)
        val update = combine(set("name", user.name), set("surname", user.surname))
        val result = mongoCollection.updateOne(filter, update)
        return if (result.modifiedCount == 1L) user else throw IllegalArgumentException("User not found ${user.id}")
    }

    fun deleteUser(user: User): DeleteResult {
        return mongoCollection.deleteOne(eq("_id", user.id))
    }
}
