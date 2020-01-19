package org.server.crud.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CrudSpringApplication

fun main(args: Array<String>) {
    runApplication<CrudSpringApplication>(*args)
}
