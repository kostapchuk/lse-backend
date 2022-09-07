package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.QuizResult
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import reactor.core.publisher.Flux


interface ResultRepository : ReactiveMongoRepository<QuizResult, String> {

    @Tailable
    fun findWithTailableCursorBy(): Flux<QuizResult>
}
