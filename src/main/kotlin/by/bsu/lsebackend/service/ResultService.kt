package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.entity.QuizResult
import by.bsu.lsebackend.extension.equalsIgnoreOrder
import by.bsu.lsebackend.repository.QuizRepository
import by.bsu.lsebackend.repository.ResultRepository
import by.bsu.lsebackend.repository.UserRepositoryFacade
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Service
class ResultService(
    private val quizRepository: QuizRepository,
    private val senderService: SenderService,
    private val resultRepository: ResultRepository,
    private val userRepositoryFacade: UserRepositoryFacade,
) {

    fun check(resultRequest: ResultRequest): Mono<Int> {
        return quizRepository.findById(resultRequest.quizResultRequest.quizId)
            .flatMap { (_, name, items, maxScore, createdDate): Quiz ->
                userRepositoryFacade.findById(resultRequest.userId, resultRequest.userType).map {
                    QuizResult(
                        quizName = name,
                        score = items.stream()
                            .map { item -> retrieveScore(item, resultRequest.quizResultRequest.items) }
                            .reduce { acc, next -> acc + next }.orElse(0),
                        maxScore = maxScore,
                        createdDate = LocalDateTime.now(),
                        email = it.email
                    )
                }
            }.flatMap {
                resultRepository.insert(it)
            }.flatMap {
//                senderService.send(
//                    resultRequest.userResultRequest.email,
//                    "Ваш результат ${it.score} из ${it.maxScore}",
//                    "Результат теста: ${it.quizName}"
//                )
                return@flatMap Mono.just(it.score)
            }
    }

    fun findWithTailableCursorBy(): Flux<QuizResult> = resultRepository.findWithTailableCursorBy()

    fun findAllByEmail(email: String, page: Long, size: Long): Flux<QuizResult> =
        resultRepository.findAllByEmail(email).sort(Comparator.comparing(QuizResult::createdDate).reversed())
            .skip(page * size).take(size)

    private fun retrieveScore(
        quizItem: Quiz.QuizItem,
        quizItemRequest: List<QuizResultRequest.QuizItemRequest>,
    ): Int = quizItemRequest.stream().filter { it.questionId == quizItem.question.id }.filter {
        it.answerIds.equalsIgnoreOrder(quizItem.answers.filter { a -> a.correct }.map(Quiz.QuizItem.Answer::id))
    }.map { quizItem.question.cost }.findFirst().orElse(0)
}
