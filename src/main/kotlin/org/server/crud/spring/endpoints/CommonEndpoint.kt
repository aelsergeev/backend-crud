package org.server.crud.spring.endpoints

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class CommonEndpoint(
    private val redis: RedisTemplate<String, String>
) {

    @GetMapping("/hello")
    fun hello(): String = "Hello, World!"

    @GetMapping("/redis")
    fun redis(): MutableSet<String> = redis.keys("*")

}
