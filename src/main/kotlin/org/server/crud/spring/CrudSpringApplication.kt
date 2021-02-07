package org.server.crud.spring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CrudSpringApplication

fun main(args: Array<String>) {
    SpringApplication.run(CrudSpringApplication::class.java, *args)
}
