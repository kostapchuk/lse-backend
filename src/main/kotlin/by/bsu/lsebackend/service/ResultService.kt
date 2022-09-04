package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.extension.equalsIgnoreOrder
import by.bsu.lsebackend.repository.QuizRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ResultService(
    private val quizRepository: QuizRepository,
    private val senderService: SenderService
) {

    fun check(resultRequest: ResultRequest): Mono<Int> =
        quizRepository.findById(resultRequest.quizResultRequest.quizId)
            .map {
                val result = it.items.stream()
                    .map { item -> retrieveScore(item, resultRequest.quizResultRequest.items) }
                    .reduce { acc, next -> acc + next }
                    .orElse(0)
                senderService.send(
                    resultRequest.userResultRequest.email,
                    "Your result is $result out of ${retrieveMaxScore(it.items)}",
                    "Результаты теста: ${it.name}"
                )
                return@map result
            }

    private fun retrieveMaxScore(items: List<Quiz.QuizItem>): Int =
        items.stream()
            .map { it.question.cost }
            .reduce { acc, next -> acc + next }
            .orElse(0)


    private fun retrieveScore(
        quizItem: Quiz.QuizItem,
        quizItemRequest: List<QuizResultRequest.QuizItemRequest>
    ): Int =
        quizItemRequest.stream()
            .filter { it.questionId == quizItem.question.id }
            .filter {
                it.answerIds.equalsIgnoreOrder(quizItem.answers.stream().filter { a -> a.correct }
                    .map(Quiz.QuizItem.Answer::id).toList())
            }
            .map { quizItem.question.cost }
            .findFirst()
            .orElse(0)

}
