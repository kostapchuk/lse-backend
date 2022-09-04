package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.extension.toEntity
import by.bsu.lsebackend.extension.toResponse
import by.bsu.lsebackend.repository.QuizRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class QuizService(private val quizRepository: QuizRepository) {

    @Cacheable(value = ["quizzes"])
    fun findAll(): Flux<QuizResponse> = quizRepository.findAll().map {
        it.toResponse()
    }

    fun create(quiz: QuizRequest): Mono<Quiz> = quizRepository.insert(quiz.toEntity())
}
