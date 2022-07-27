package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveMongoRepository<User, String> {
    fun findByFirstName(firstName: String): Mono<User>
}