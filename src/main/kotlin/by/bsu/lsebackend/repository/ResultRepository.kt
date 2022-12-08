package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.QuizResult
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface ResultRepository : ReactiveMongoRepository<QuizResult, String> {

    fun findAllByEmail(email: String): Flux<QuizResult>
}
