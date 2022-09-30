package by.bsu.lsebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@EnableCaching
class LseBackendApplication

fun main(args: Array<String>) {
    runApplication<LseBackendApplication>(*args)
}
