package org.server.crud.spring.configs

import org.server.crud.spring.models.User
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfig(
    private val kafkaProperties: KafkaProperties
) {

    @Bean
    fun kafkaProducerFactory(): ProducerFactory<String, User> {
        return DefaultKafkaProducerFactory(kafkaProperties.buildProducerProperties())
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, User> {
        return KafkaTemplate(kafkaProducerFactory())
    }

}
