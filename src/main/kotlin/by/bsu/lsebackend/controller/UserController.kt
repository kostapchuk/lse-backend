package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.LoginRequest
import by.bsu.lsebackend.dto.LoginResponse
import by.bsu.lsebackend.dto.RefreshTokenRequest
import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.TeacherRequest
import by.bsu.lsebackend.service.UserService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class UserController(private val userService: UserService) {

    @PostMapping("/login")
    fun loginTeacher(@RequestBody @Validated loginRequest: LoginRequest): Mono<LoginResponse> =
        userService.login(loginRequest)

    @PostMapping("/register-student")
    @ResponseStatus(CREATED)
    fun registerStudent(@RequestBody studentRequest: StudentRequest): Mono<RegisterResponse> =
        userService.registerStudent(studentRequest)

    @PostMapping("/register-teacher")
    @ResponseStatus(CREATED)
    fun registerTeacher(@RequestBody teacherRequest: TeacherRequest): Mono<RegisterResponse> =
        userService.registerTeacher(teacherRequest)

    @PostMapping("/refresh-token-teacher")
    fun refreshTokenTeacher(@RequestBody @Validated refreshToken: RefreshTokenRequest): Mono<LoginResponse> =
        userService.refreshToken(refreshToken)

}
