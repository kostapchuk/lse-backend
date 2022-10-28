package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.mapper.QuizMapper
import by.bsu.lsebackend.repository.QuizRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class QuizService(private val quizRepository: QuizRepository, private val quizMapper: QuizMapper) {

    @Cacheable("quizzes")
    fun findAllPaged(page: Long, size: Long): Flux<QuizResponse> =
        quizRepository.findAll()
            .sort(Comparator.comparing(Quiz::createdDate).reversed())
            .skip(page * size)
            .take(size)
            .map { quizMapper.toResponse(it) }

    fun create(quiz: QuizRequest): Mono<Quiz> = quizRepository.save(quizMapper.toEntity(quiz))
}
