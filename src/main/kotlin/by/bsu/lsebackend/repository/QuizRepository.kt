package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.Quiz
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface QuizRepository : ReactiveMongoRepository<Quiz, String> {
    fun findTop20ByOrderByCreatedDate(): Flux<Quiz>
}
