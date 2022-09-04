package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.service.QuizService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/quizzes")
class QuizController(private val quizService: QuizService) {

    @GetMapping
    fun findAll(): Flux<QuizResponse> = quizService.findAll()

    @PostMapping
    @ResponseStatus(CREATED)
    fun create(@RequestBody quiz: QuizRequest,): Mono<Quiz> = quizService.create(quiz)

}
