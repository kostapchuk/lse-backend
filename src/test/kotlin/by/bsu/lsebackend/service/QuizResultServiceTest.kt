package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuestionAndAnswersRequest
import by.bsu.lsebackend.dto.QuizResult
import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.dto.UserQuizRequest
import by.bsu.lsebackend.entity.Answer
import by.bsu.lsebackend.entity.Question
import by.bsu.lsebackend.entity.QuestionAndAnswers
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.repository.QuizRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

//internal??
@SpringBootTest
@ActiveProfiles("test")
class QuizResultServiceTest(@Autowired private val quizResultService: QuizResultService) {

    @MockBean
    private lateinit var quizRepository: QuizRepository

    private var quiz: Quiz = Quiz(
        "quizId", "Первая помощь утопающим", listOf(
            QuestionAndAnswers(
                Question("1", "Question 1", false, 5),
                listOf(
                    Answer("a1", "answer1", false), Answer("a2", "answer2", true),
                    Answer("a3", "answer3", false)
                )
            ), QuestionAndAnswers(
                Question("11", "Question 11", true, 10),
                listOf(
                    Answer("a11", "answer11", false), Answer("a22", "answer22", true),
                    Answer("a33", "answer33", true)
                )
            ),
            QuestionAndAnswers(
                Question("111", "Question 111", false, 7),
                listOf(
                    Answer("a111", "answer111", false), Answer("a222", "answer222", false),
                    Answer("a333", "answer333", true)
                )
            )
        )
    )
    private var quizResultRequest: QuizResultRequest =
        QuizResultRequest(
            QuizResult(
                "quizId",
                listOf(
                    QuestionAndAnswersRequest("1", listOf("a3")),
                    QuestionAndAnswersRequest("11", listOf("a33", "a22")),
                    QuestionAndAnswersRequest("111", listOf("a333"))
                )
            ),
            UserQuizRequest("Kirill", "Ostapchuk", "kera.ostapchuk2@mail.ru", "123q", "FSC")
        )

    @Test
    fun validateQuizResult() {
        Mockito.`when`(quizRepository.findById("quizId")).then { Mono.just(quiz) }
        StepVerifier.create(quizResultService.validate(quizResultRequest))
            .expectSubscription()
            .expectNext(17)
            .verifyComplete()
    }
}
