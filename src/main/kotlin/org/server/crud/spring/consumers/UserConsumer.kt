package org.server.crud.spring.consumers

import org.server.crud.spring.models.User
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class UserConsumer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(id = "crud", topics = ["user"])
    @RabbitListener(queues = ["user"])
    fun handleCreate(user: User) {
        logger.info("User created: $user")
    }
}
