package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.QuizResultRequest
import by.bsu.lsebackend.service.QuizResultService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/quiz-result")
class QuizResultController(private val quizResultService: QuizResultService) {

    @PostMapping
    fun validate(@RequestBody quizResultRequest: QuizResultRequest) = quizResultService.validate(quizResultRequest)
}
