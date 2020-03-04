package org.server.crud.spring.dao

import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.server.crud.spring.models.User
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class UserDao(private val mongoTemplate: MongoTemplate) {

    fun getUserById(id: ObjectId): User {
        val query = Query().apply { addCriteria(Criteria.where("_id").`is`(id)) }
        return mongoTemplate.find(query, User::class.java).first() ?: throw IllegalArgumentException("User not found $id")
    }

    fun getUserList(): List<User> {
        return mongoTemplate.findAll(User::class.java).toList()
    }

    fun createUser(user: User): User {
        return mongoTemplate.insert(user)
    }

    fun updateUser(user: User): User {
        val query = Query().apply { addCriteria(Criteria.where("_id").`is`(user.id)) }
        val update = Update().apply { set("name", user.name); set("surname", user.surname) }
        val result = mongoTemplate.updateFirst(query, update, User::class.java)
        return if (result.modifiedCount == 1L) user else throw IllegalArgumentException("User not found ${user.id}")
    }

    fun deleteUser(user: User): DeleteResult {
        val query = Query().apply { addCriteria(Criteria.where("_id").`is`(user.id)) }
        return mongoTemplate.remove(query, User::class.java)
    }

}
