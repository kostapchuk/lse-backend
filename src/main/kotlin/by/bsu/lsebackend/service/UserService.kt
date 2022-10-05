package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.LoginRequest
import by.bsu.lsebackend.dto.LoginResponse
import by.bsu.lsebackend.dto.RefreshTokenRequest
import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.StudentResponse
import by.bsu.lsebackend.dto.TeacherRequest
import by.bsu.lsebackend.dto.TeacherResponse
import by.bsu.lsebackend.entity.Student
import by.bsu.lsebackend.entity.Teacher
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.extension.toEntity
import by.bsu.lsebackend.extension.toResponse
import by.bsu.lsebackend.repository.StudentRepository
import by.bsu.lsebackend.repository.TeacherRepository
import by.bsu.lsebackend.security.TokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val teacherRepository: TeacherRepository,
    private val studentRepository: StudentRepository,
    private val tokenService: TokenService,
    private val passwordEncoder: PasswordEncoder,
) {

    fun registerStudent(studentRequest: StudentRequest): Mono<StudentResponse> = Mono.just(studentRequest)
        .flatMap { studentRepository.findByEmailAndUserType(it.email) }
        .flatMap<StudentResponse> {
            Mono.error(BadRequestException("User with ${it.email} already exists"))
        }.switchIfEmpty(
            Mono.just(studentRequest).map { it.toEntity() }.flatMap {
                it.password = passwordEncoder.encode(it.password)
                studentRepository.insert(it)
            }.map { it.toResponse() }
        )

    fun registerTeacher(teacherRequest: TeacherRequest): Mono<TeacherResponse> = Mono.just(teacherRequest)
        .flatMap { teacherRepository.findByEmailAndUserType(it.email, it.userType) }
        .flatMap<TeacherResponse> {
            Mono.error(BadRequestException("User with ${it.email} already exists"))
        }.switchIfEmpty(
            Mono.just(teacherRequest).map { it.toEntity() }.map {
                it.password = passwordEncoder.encode(it.password)
                println(it)
                it
            }.flatMap { teacherRepository.insert(it) }.map { it.toResponse() }
        )


    fun loginTeacher(loginRequest: LoginRequest): Mono<LoginResponse> =
        teacherRepository.findByEmailAndUserType(loginRequest.email)
            .filter { passwordEncoder.matches(loginRequest.password, it.password) }
            .flatMap {
                updateRefreshTokenTeacher(it)
            }.map {
                LoginResponse(tokenService.generateAccessToken(it), it.refreshToken)
            }
            .switchIfEmpty(
                Mono.error(BadRequestException("Credentials are invalid"))
            )

    fun loginStudent(loginRequest: LoginRequest): Mono<LoginResponse> =
        studentRepository.findByEmailAndUserType(loginRequest.email)
            .filter { passwordEncoder.matches(loginRequest.password, it.password) }
            .flatMap {
                updateRefreshTokenStudent(it)
            }.map {
                LoginResponse(tokenService.generateAccessToken(it), it.refreshToken)
            }
            .switchIfEmpty(
                Mono.error(BadRequestException("Credentials are invalid"))
            )

    fun refreshTokenStudent(refreshToken: RefreshTokenRequest): Mono<LoginResponse> =
        tokenService.isValid(refreshToken.refreshToken).flatMap {
            studentRepository.findByRefreshTokenAndUserType(refreshToken.refreshToken)
                .flatMap {
                    updateRefreshTokenStudent(it)
                }.map {
                    LoginResponse(tokenService.generateAccessToken(it), it.refreshToken)
                }
        }.switchIfEmpty(Mono.error(BadRequestException("Refresh token is invalid")))

    fun refreshTokenTeacher(refreshToken: RefreshTokenRequest): Mono<LoginResponse> =
        tokenService.isValid(refreshToken.refreshToken).flatMap {
            teacherRepository.findByRefreshTokenAndUserType(refreshToken.refreshToken)
                .flatMap {
                    updateRefreshTokenTeacher(it)
                }.map {
                    LoginResponse(tokenService.generateAccessToken(it), it.refreshToken)
                }
        }.switchIfEmpty(Mono.error(BadRequestException("Refresh token is invalid")))

    private fun updateRefreshTokenTeacher(it: Teacher): Mono<Teacher> {
        val refreshToken = tokenService.generateRefreshToken(it)
        it.refreshToken = refreshToken
        return teacherRepository.save(it)
    }

    private fun updateRefreshTokenStudent(it: Student): Mono<Student> {
        val refreshToken = tokenService.generateRefreshToken(it)
        it.refreshToken = refreshToken
        return studentRepository.save(it)
    }

}
