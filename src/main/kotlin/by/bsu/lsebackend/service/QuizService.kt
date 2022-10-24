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

    // todo remove comments
    @Cacheable("quizzes")
    fun findAllPaged(page: Long, size: Long): Flux<QuizResponse> =
        quizRepository.findAll()
            .sort(Comparator.comparing(Quiz::createdDate).reversed())
            .skip(page * size)
            .take(size)
            .map { it.toResponse() }
//            .replay(10)
//            .autoConnect(0)

    fun create(quiz: QuizRequest): Mono<Quiz> = quizRepository.save(quiz.toEntity())

    @Cacheable("topics")
    fun findTop20Topics(): Mono<List<String>> =
        quizRepository.findTop20ByOrderByCreatedDate().map { it.name }.collectList()
}
