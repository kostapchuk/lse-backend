package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.service.QuizService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/quizzes")
class QuizController(private val quizService: QuizService) {

    @GetMapping("/topics")
    fun findTop20Topics(): Mono<List<String>> = quizService.findTop20Topics()

    @GetMapping
    @PreAuthorize("#{hasAnyRole(T(by.bsu.lsebackend.entity.UserRole).ROLE_TEACHER.getRoleWithoutPrefix()," +
            "T(by.bsu.lsebackend.entity.UserRole).ROLE_STUDENT.getRoleWithoutPrefix())}")
    fun findAllPaged(
        @RequestParam(value = "page", defaultValue = "0") page: Long,
        @RequestParam(value = "size", defaultValue = "10") size: Long,
    ): Flux<QuizResponse> = quizService.findAllPaged(page, size)

    @PostMapping
    @ResponseStatus(CREATED)
    @PreAuthorize("#{hasAnyRole(T(by.bsu.lsebackend.entity.UserRole).ROLE_TEACHER.getRoleWithoutPrefix())}")
    fun create(@RequestBody @Validated quiz: QuizRequest): Mono<Quiz> = quizService.create(quiz)
}
