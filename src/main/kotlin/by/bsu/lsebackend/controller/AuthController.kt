package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.LoginRequest
import by.bsu.lsebackend.dto.LoginResponse
import by.bsu.lsebackend.dto.RefreshTokenRequest
import by.bsu.lsebackend.service.AuthService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody @Validated loginRequest: LoginRequest): Mono<LoginResponse> =
        authService.login(loginRequest)

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody @Validated refreshToken: RefreshTokenRequest): Mono<LoginResponse> =
        authService.refreshToken(refreshToken)
}
