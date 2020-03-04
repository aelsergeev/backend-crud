package org.server.crud.spring.service

import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.server.crud.spring.dao.UserDao
import org.server.crud.spring.models.User
import org.server.crud.spring.models.UserCreateRequest
import org.server.crud.spring.models.UserDeleteRequest
import org.server.crud.spring.models.UserUpdateRequest
import org.springframework.stereotype.Service

@Service
class UserService(private val userDao: UserDao) {
    fun getUserById(id: ObjectId): User = userDao.getUserById(id)

    fun getUsers(): List<User> = userDao.getUserList()

    fun createUser(user: UserCreateRequest): User = userDao.createUser(User(user))

    fun updateUser(user: UserUpdateRequest): User = userDao.updateUser(User(user))

    fun deleteUser(user: UserDeleteRequest): DeleteResult = userDao.deleteUser(User(user))
}
