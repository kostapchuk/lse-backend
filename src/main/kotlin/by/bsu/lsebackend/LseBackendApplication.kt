package by.bsu.lsebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

// todo add controller advice
// todo change logic to have cost for answer not for question

@SpringBootApplication
class LseBackendApplication

fun main(args: Array<String>) {
    runApplication<LseBackendApplication>(*args)
}
