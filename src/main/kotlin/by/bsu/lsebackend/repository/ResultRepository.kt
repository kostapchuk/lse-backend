package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.QuizResult
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.mongodb.repository.Tailable
import reactor.core.publisher.Flux

interface ResultRepository : ReactiveMongoRepository<QuizResult, String> {

    // todo: add pagination and more precise fetching (by test, by group)

    // db.createCollection("results", { capped : true, size : 5242880, max : 50 } )

    @Tailable
    fun findWithTailableCursorBy(): Flux<QuizResult>

    fun findAllByEmail(email: String): Flux<QuizResult>
}
