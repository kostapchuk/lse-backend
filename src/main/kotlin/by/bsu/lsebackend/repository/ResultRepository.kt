package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.QuizResult
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import reactor.core.publisher.Flux

interface ResultRepository : ReactiveMongoRepository<QuizResult, String> {

    // todo: add pagination and more precise fetching (by test, by group)

    @Tailable
    fun findWithTailableCursorBy(): Flux<QuizResult>

    fun findAllByEmail(email: String): Flux<QuizResult>
}
