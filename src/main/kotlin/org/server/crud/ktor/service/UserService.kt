package org.server.crud.ktor.service

import org.bson.types.ObjectId
import org.server.crud.ktor.dao.UserDao
import org.server.crud.ktor.models.User

class UserService {
    private val userDao = UserDao()

    fun getUserById(id: ObjectId): User {
        return userDao.getUserById(id)
    }

    fun getUsers(): List<User> {
        return userDao.getUserList()
    }
}
