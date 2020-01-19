package org.server.crud.spring.endpoints

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloEndpoint {

    @GetMapping("/")
    fun hello(): String = "Hello, World!"

}