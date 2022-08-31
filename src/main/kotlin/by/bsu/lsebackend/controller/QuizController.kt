package by.bsu.lsebackend.controller

import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.service.QuizService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/quizzes")
class QuizController(private val quizService: QuizService) {

    @GetMapping
    @CrossOrigin(origins = ["http://localhost:3000"])
    fun findAll() = quizService.findAll()

    @PostMapping
    fun create(@RequestBody quiz: Quiz) = quizService.create(quiz)

}
