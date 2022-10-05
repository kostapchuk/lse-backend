package by.bsu.lsebackend

import by.bsu.lsebackend.properties.EmailSenderProperties
import by.bsu.lsebackend.properties.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableAsync

// todo separate into 2 entities: teacher and student
// todo add validation for input
// todo try to convert TokenService to functional style
// write tests

@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableConfigurationProperties(JwtProperties::class, EmailSenderProperties::class)
class LseBackendApplication

fun main(args: Array<String>) {
    runApplication<LseBackendApplication>(*args)
}
