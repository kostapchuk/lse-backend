package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuestionAndAnswersRequest
import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.entity.Answer
import by.bsu.lsebackend.entity.Question
import by.bsu.lsebackend.entity.QuestionAndAnswers
import by.bsu.lsebackend.repository.QuizRepository
import org.springframework.stereotype.Service

@Service
class QuizResultService(
    private val quizRepository: QuizRepository,
    private val senderService: SenderService
) {

    fun validate(quizResultRequest: QuizResultRequest) =
        quizRepository.findById(quizResultRequest.quizResult.id)
            .map {
                val result = it.questionsAndAnswers.stream()
                    .map { qAndA -> retrieveScore(qAndA, quizResultRequest.quizResult.questionAndAnswersResult) }
                    .reduce { acc, next -> acc + next }
                    .orElse(0)
                val maxScore =
                    it.questionsAndAnswers.stream().map(QuestionAndAnswers::question).map(Question::cost).reduce { acc, next -> acc + next }
                        .orElse(0)
                senderService.send(
                    quizResultRequest.userQuizRequest.email,
                    "Your result is $result out of $maxScore",
                    "Результаты теста: ${it.name}"
                )
                return@map result
            }


    private fun retrieveScore(
        questionAndAnswers: QuestionAndAnswers,
        questionAndAnswersRequest: List<QuestionAndAnswersRequest>
    ): Int =
        questionAndAnswersRequest.stream()
            .filter { it.questionId == questionAndAnswers.question.id }
            .filter {
                it.answerIds.equalsIgnoreOrder(questionAndAnswers.answers.stream().filter { a -> a.correct }
                    .map(Answer::id).toList())
            }
            .map { questionAndAnswers.question.cost }
            .findFirst()
            .orElse(0)

    infix fun <T> List<T>.equalsIgnoreOrder(other: List<T>) = this.size == other.size && this.toSet() == other.toSet()
}
