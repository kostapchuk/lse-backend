package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.LoginRequest
import by.bsu.lsebackend.dto.RefreshTokenRequest
import by.bsu.lsebackend.dto.StudentRequest
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
    fun login(@RequestBody @Validated loginRequest: LoginRequest) =
        userService.login(loginRequest)

    @PostMapping("/register")
    @ResponseStatus(CREATED)
    fun register(@RequestBody @Validated studentRequest: Mono<StudentRequest>) =
        userService.register(studentRequest)

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody @Validated refreshToken: RefreshTokenRequest) =
        userService.refreshToken(refreshToken)

//    @GetMapping("/student")
//    @PreAuthorize("hasRole('STUDENT')")
//    fun test() = Flux.just("adasd", "asdas", "eqweqwe")
//
//    @GetMapping("/teacher")
//    @PreAuthorize("hasRole('TEACHER')")
//    fun test1() = Flux.just("teacher", "teacher", "student")
}
