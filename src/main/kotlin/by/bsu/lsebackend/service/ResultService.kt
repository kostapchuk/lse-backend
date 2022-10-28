package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.dto.QuizResultResponse
import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.entity.QuizResult
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.extension.equalsIgnoreOrder
import by.bsu.lsebackend.mapper.QuizResultMapper
import by.bsu.lsebackend.repository.QuizRepository
import by.bsu.lsebackend.repository.ResultRepository
import by.bsu.lsebackend.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class ResultService(
    private val quizRepository: QuizRepository,
    private val resultRepository: ResultRepository,
    private val userRepository: UserRepository,
    private val quizResultMapper: QuizResultMapper,
) {

    fun check(resultRequest: ResultRequest): Mono<Int> {
        return quizRepository.findById(resultRequest.quizResultRequest.quizId)
            .flatMap { (_, name, items, maxScore, createdDate): Quiz ->
                toQuizResult(resultRequest, name, items, maxScore)
            }.flatMap {
                resultRepository.insert(it).map { result -> result.score }
            }
    }

    fun findAllByEmail(email: String, page: Long, size: Long): Flux<QuizResultResponse> =
        resultRepository.findAllByEmail(email).sort(Comparator.comparing(QuizResult::createdDate).reversed())
            .skip(page * size).take(size).map { quizResultMapper.toResponse(it) }

    fun findAll(page: Long, size: Long): Flux<QuizResultResponse> =
        resultRepository.findAll().sort(Comparator.comparing(QuizResult::createdDate).reversed())
            .skip(page * size).take(size).map { quizResultMapper.toResponse(it) }

    private fun retrieveScore(
        quizItem: Quiz.QuizItem,
        quizItemRequest: List<QuizResultRequest.QuizItemRequest>,
    ): Int = quizItemRequest.stream().filter { it.questionId == quizItem.question.id }.filter {
        it.answerIds.equalsIgnoreOrder(quizItem.answers.filter { a -> a.correct }.map(Quiz.QuizItem.Answer::id))
    }.map { quizItem.question.cost }.findFirst().orElse(0)

    private fun toQuizResult(
        resultRequest: ResultRequest,
        name: String,
        items: List<Quiz.QuizItem>,
        maxScore: Int,
    ) = userRepository.findByIdAndUserType(resultRequest.userId, resultRequest.userType)
        .switchIfEmpty(Mono.error(BadRequestException("User with id ${resultRequest.userId} does not exist")))
        .map {
            QuizResult(
                quizName = name,
                score = items.stream()
                    .map { item -> retrieveScore(item, resultRequest.quizResultRequest.items) }
                    .reduce { acc, next -> acc + next }.orElse(0),
                maxScore = maxScore,
                createdDate = LocalDateTime.now(),
                email = it.email,
            )
        }
}
