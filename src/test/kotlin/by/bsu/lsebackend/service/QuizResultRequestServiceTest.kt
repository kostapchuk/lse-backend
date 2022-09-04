package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.dto.UserResultRequest
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
class QuizResultRequestServiceTest(@Autowired private val resultService: ResultService) {

    @MockBean
    private lateinit var quizRepository: QuizRepository

    private var quiz: Quiz = Quiz(
        "quizId", "Первая помощь утопающим", listOf(
            Quiz.QuizItem(
                Quiz.QuizItem.Question("1", "Question 1", false, 5),
                listOf(
                    Quiz.QuizItem.Answer("a1", "answer1", false),
                    Quiz.QuizItem.Answer("a2", "answer2", true),
                    Quiz.QuizItem.Answer("a3", "answer3", false)
                )
            ), Quiz.QuizItem(
                Quiz.QuizItem.Question("11", "Question 11", true, 10),
                listOf(
                    Quiz.QuizItem.Answer("a11", "answer11", false),
                    Quiz.QuizItem.Answer("a22", "answer22", true),
                    Quiz.QuizItem.Answer("a33", "answer33", true)
                )
            ),
            Quiz.QuizItem(
                Quiz.QuizItem.Question("111", "Question 111", false, 7),
                listOf(
                    Quiz.QuizItem.Answer("a111", "answer111", false),
                    Quiz.QuizItem.Answer("a222", "answer222", false),
                    Quiz.QuizItem.Answer("a333", "answer333", true)
                )
            )
        )
    )
    private var resultRequest: ResultRequest =
        ResultRequest(
            QuizResultRequest(
                "quizId",
                listOf(
                    QuizResultRequest.QuizItemRequest("1", listOf("a3")),
                    QuizResultRequest.QuizItemRequest("11", listOf("a33", "a22")),
                    QuizResultRequest.QuizItemRequest("111", listOf("a333"))
                )
            ),
            UserResultRequest("Kirill", "Ostapchuk", "kera.ostapchuk2@mail.ru", "123q", "FSC")
        )

    @Test
    fun validateQuizResult() {
        Mockito.`when`(quizRepository.findById("quizId")).then { Mono.just(quiz) }
        StepVerifier.create(resultService.check(resultRequest))
            .expectSubscription()
            .expectNext(17)
            .verifyComplete()
    }
}
