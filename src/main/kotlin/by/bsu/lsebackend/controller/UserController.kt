package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.TeacherRequest
import by.bsu.lsebackend.service.UserService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @PostMapping("/teachers")
    @ResponseStatus(CREATED)
    fun registerTeacher(@RequestBody @Validated request: TeacherRequest): Mono<RegisterResponse> =
        userService.register(request)

    @PostMapping("/students")
    @ResponseStatus(CREATED)
    fun registerStudent(@RequestBody @Validated request: StudentRequest): Mono<RegisterResponse> =
        userService.register(request)
}
