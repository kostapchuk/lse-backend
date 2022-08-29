package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuestionAndAnswersRequest
import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.entity.Answer
import by.bsu.lsebackend.entity.QuestionAndAnswers
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.repository.QuizRepository
import org.springframework.stereotype.Service

@Service
class QuizResultService(private val quizRepository: QuizRepository) {

    // send email with the result

    fun validate(quizResultRequest: QuizResultRequest) =
        quizRepository.findById(quizResultRequest.quizResult.quizId)
            .map(Quiz::questionsAndAnswers)
            .map {
                it.stream()
                    .map { qAndA -> retrieveScore(qAndA, quizResultRequest.quizResult.questionAndAnswersRequest) }
                    .reduce { acc, next -> acc + next }
                    .orElse(0)
            }
//            .map { sendEmailToUser}


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