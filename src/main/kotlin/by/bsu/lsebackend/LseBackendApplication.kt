package by.bsu.lsebackend

import by.bsu.lsebackend.properties.EmailSenderProperties
import by.bsu.lsebackend.properties.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableAsync

// todo test responses to have body (status, timestamp, message)
// todo log exceptions
// todo add validation for input
// todo try to convert TokenService to functional style
// todo write tests
// todo add mono/flux to parameter in controllers

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableConfigurationProperties(JwtProperties::class, EmailSenderProperties::class)
class LseBackendApplication

fun main(args: Array<String>) {
    runApplication<LseBackendApplication>(*args)
}
