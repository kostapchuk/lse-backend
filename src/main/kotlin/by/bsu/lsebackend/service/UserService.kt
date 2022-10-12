package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.LoginRequest
import by.bsu.lsebackend.dto.LoginResponse
import by.bsu.lsebackend.dto.RefreshTokenRequest
import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.TeacherRequest
import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.entity.Student
import by.bsu.lsebackend.entity.Teacher
import by.bsu.lsebackend.entity.UserType
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.extension.toEntity
import by.bsu.lsebackend.extension.toResponse
import by.bsu.lsebackend.repository.StudentRepository
import by.bsu.lsebackend.repository.TeacherRepository
import by.bsu.lsebackend.security.TokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class UserService(
    private val teacherRepository: TeacherRepository,
    private val studentRepository: StudentRepository,
    private val tokenService: TokenService,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun registerStudent(studentRequest: StudentRequest): Mono<RegisterResponse> =
        findByEmail(studentRequest.email, studentRequest.userType)
            .flatMap<RegisterResponse> {
                Mono.error(BadRequestException("User with ${it.email} already exists"))
            }.switchIfEmpty(
                Mono.just(studentRequest).map { it.toEntity() }.flatMap {
                    it.password = passwordEncoder.encode(it.password)
                    studentRepository.save(it)
                }.map { it.toResponse() }
            )

    @Transactional
    fun registerTeacher(request: TeacherRequest): Mono<RegisterResponse> =
        findByEmail(request.email, request.userType)
            .flatMap<RegisterResponse> {
                Mono.error(BadRequestException("User with ${it.email} already exists"))
            }.switchIfEmpty(
                Mono.just(request).map { it.toEntity() }.flatMap {
                    it.password = passwordEncoder.encode(it.password)
                    save(it, it.userType)
                }.map { it.toResponse() }
            )

    @Transactional
    fun login(loginRequest: LoginRequest): Mono<LoginResponse> =
        findByEmail(loginRequest.email, loginRequest.userType)
            .filter { passwordEncoder.matches(loginRequest.password, it.password) }
            .flatMap {
                updateRefreshToken(it)
            }.map {
                LoginResponse(tokenService.generateAccessToken(it), it.refreshToken, it.id, it.userType, it.role)
            }
            .switchIfEmpty(
                Mono.error(BadRequestException("Credentials are invalid"))
            )

    @Transactional
    fun refreshToken(refreshToken: RefreshTokenRequest): Mono<LoginResponse> =
        tokenService.isValid(refreshToken.refreshToken).flatMap {
            findByRefreshToken(refreshToken)
                .flatMap {
                    updateRefreshToken(it)
                }.map {
                    LoginResponse(tokenService.generateAccessToken(it), it.refreshToken, it.id, it.userType, it.role)
                }
        }.switchIfEmpty(Mono.error(BadRequestException("Refresh token is invalid")))

    private fun <T : BaseUser> updateRefreshToken(it: T): Mono<out BaseUser> {
        val refreshToken = tokenService.generateRefreshToken(it)
        it.refreshToken = refreshToken
        return when (it.userType) {
            UserType.STUDENT -> {
                studentRepository.save(it as Student)
            }
            UserType.TEACHER -> {
                teacherRepository.save(it as Teacher)
            }
        }
    }

    private fun <T : BaseUser> save(t: T, userType: UserType): Mono<out BaseUser> {
        return when (userType) {
            UserType.STUDENT -> {
                studentRepository.save(t as Student)
            }
            UserType.TEACHER -> {
                teacherRepository.save(t as Teacher)
            }
        }
    }

    private fun findByEmail(email: String, userType: UserType): Mono<out BaseUser> {
        return when (userType) {
            UserType.STUDENT -> {
                studentRepository.findByEmailAndUserType(email)
            }
            UserType.TEACHER -> {
                teacherRepository.findByEmailAndUserType(email)
            }
        }
    }

    private fun findByRefreshToken(refreshToken: RefreshTokenRequest): Mono<out BaseUser> {
        return when (refreshToken.userType) {
            UserType.STUDENT -> {
                studentRepository.findByRefreshTokenAndUserType(refreshToken.refreshToken)
            }
            UserType.TEACHER -> {
                teacherRepository.findByRefreshTokenAndUserType(refreshToken.refreshToken)
            }
        }
    }
}
