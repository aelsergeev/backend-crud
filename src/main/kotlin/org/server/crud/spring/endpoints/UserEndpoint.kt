package org.server.crud.spring.endpoints

import com.mongodb.client.result.DeleteResult
import org.bson.types.ObjectId
import org.server.crud.spring.models.User
import org.server.crud.spring.models.UserCreateRequest
import org.server.crud.spring.models.UserDeleteRequest
import org.server.crud.spring.models.UserUpdateRequest
import org.server.crud.spring.services.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserEndpoint(private val userService: UserService) {

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: String): User {
        return userService.getUserById(ObjectId(id))
    }

    @GetMapping("/list")
    fun getUserList(): List<User> {
       return userService.getUsers()
    }

    @PutMapping
    fun createUser(@RequestBody userCreateRequest: UserCreateRequest): User {
        return userService.createUser(userCreateRequest)
    }

    @PostMapping
    fun updateUser(@RequestBody userUpdateRequest: UserUpdateRequest): User {
        return userService.updateUser(userUpdateRequest)
    }

    @DeleteMapping
    fun deleteUser(@RequestBody userDeleteRequest: UserDeleteRequest): DeleteResult {
        return userService.deleteUser(userDeleteRequest)
    }

}
