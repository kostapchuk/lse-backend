package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.entity.QuizResult
import by.bsu.lsebackend.extension.equalsIgnoreOrder
import by.bsu.lsebackend.repository.QuizRepository
import by.bsu.lsebackend.repository.ResultRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ResultService(
    private val quizRepository: QuizRepository,
    private val senderService: SenderService,
    private val resultRepository: ResultRepository,
) {

    fun check(resultRequest: ResultRequest): Mono<Int> {
        return quizRepository.findById(resultRequest.quizResultRequest.quizId)
            .map {
                QuizResult(
                    quizName = it.name,
                    firstName = resultRequest.userResultRequest.firstName,
                    lastName = resultRequest.userResultRequest.lastName,
                    group = resultRequest.userResultRequest.group,
                    faculty = resultRequest.userResultRequest.faculty,
                    email = resultRequest.userResultRequest.email,
                    score = it.items.stream()
                        .map { item -> retrieveScore(item, resultRequest.quizResultRequest.items) }
                        .reduce { acc, next -> acc + next }
                        .orElse(0),
                    maxScore = it.maxScore,
                )
            }
            .flatMap {
                resultRepository.insert(it)
            }.flatMap {
                senderService.send(
                    resultRequest.userResultRequest.email,
                    "Ваш результат ${it.score} из ${it.maxScore}",
                    "Результат теста: ${it.quizName}"
                )
                return@flatMap Mono.just(it.score)
            }
    }

    fun findWithTailableCursorBy(): Flux<QuizResult> = resultRepository.findWithTailableCursorBy()

    private fun retrieveScore(
        quizItem: Quiz.QuizItem,
        quizItemRequest: List<QuizResultRequest.QuizItemRequest>,
    ): Int =
        quizItemRequest.stream()
            .filter { it.questionId == quizItem.question.id }
            .filter {
                it.answerIds.equalsIgnoreOrder(
                    quizItem.answers.stream().filter { a -> a.correct }
                        .map(Quiz.QuizItem.Answer::id).toList()
                )
            }
            .map { quizItem.question.cost }
            .findFirst()
            .orElse(0)
}
