package org.server.crud.spring.services

import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.server.crud.spring.daos.UserDao
import org.server.crud.spring.models.User
import org.server.crud.spring.models.UserCreateRequest
import org.server.crud.spring.models.UserDeleteRequest
import org.server.crud.spring.models.UserUpdateRequest
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val userDao: UserDao,
    private val redisTemplate: RedisTemplate<String, String>,
    private val kafkaTemplate: KafkaTemplate<String, User>,
    private val rabbitTemplate: RabbitTemplate
) {
    fun getUserById(id: ObjectId): User = userDao.getUserById(id)

    fun getUsers(): List<User> = userDao.getUserList()

    @Transactional
    fun createUser(userCreateRequest: UserCreateRequest): User {
        if (redisTemplate.hasKey(userCreateRequest.name)) throw ResponseStatusException(HttpStatus.LOCKED, "User name: ${userCreateRequest.name} is locked")

        val user = userDao.createUser(User(userCreateRequest))

        redisTemplate.opsForValue()[user.name] = "1"
        kafkaTemplate.send("user", user)
        rabbitTemplate.convertAndSend("user", user)

        return user
    }

    fun updateUser(user: UserUpdateRequest): User = userDao.updateUser(User(user))

    @Transactional
    fun deleteUser(user: UserDeleteRequest): DeleteResult {
        redisTemplate.delete(getUserById(ObjectId(user.id)).name)
        return userDao.deleteUser(User(user))
    }
}
