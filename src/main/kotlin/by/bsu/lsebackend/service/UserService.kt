package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.LoginRequest
import by.bsu.lsebackend.dto.LoginResponse
import by.bsu.lsebackend.dto.RefreshTokenRequest
import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.StudentResponse
import by.bsu.lsebackend.entity.Student
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.extension.toEntity
import by.bsu.lsebackend.extension.toResponse
import by.bsu.lsebackend.repository.StudentRepository
import by.bsu.lsebackend.security.TokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val studentRepository: StudentRepository,
    private val tokenService: TokenService,
    private val passwordEncoder: PasswordEncoder,
) {

    fun register(studentRequest: Mono<StudentRequest>): Mono<StudentResponse> = studentRequest
        .flatMap { studentRepository.findByEmail(it.email) }
        .flatMap<StudentResponse> {
            Mono.error(BadRequestException("User with ${it.email} already exists"))
        }.switchIfEmpty(
            studentRequest.map { it.toEntity() }.flatMap {
                it.password = passwordEncoder.encode(it.password)
                studentRepository.insert(it)
            }.map { it.toResponse() }
        )


    fun login(loginRequest: LoginRequest): Mono<LoginResponse> =
        studentRepository.findByEmail(loginRequest.email)
            .filter { passwordEncoder.matches(loginRequest.password, it.password) }
            .flatMap {
                updateRefreshToken(it)
            }.map {
                LoginResponse(tokenService.generateAccessToken(it), it.refreshToken)
            }
            .switchIfEmpty(
                Mono.error(BadRequestException("Credentials are invalid"))
            )

    fun refreshToken(refreshToken: RefreshTokenRequest): Mono<LoginResponse> =
        tokenService.isValid(refreshToken.refreshToken).flatMap {
            studentRepository.findByRefreshToken(refreshToken.refreshToken)
                .flatMap {
                    updateRefreshToken(it)
                }.map {
                    LoginResponse(tokenService.generateAccessToken(it), it.refreshToken)
                }
        }.switchIfEmpty(Mono.error(BadRequestException("Refresh token is invalid")))

    private fun updateRefreshToken(it: Student): Mono<Student> {
        val refreshToken = tokenService.generateRefreshToken(it)
        it.refreshToken = refreshToken
        return studentRepository.save(it)
    }
}
