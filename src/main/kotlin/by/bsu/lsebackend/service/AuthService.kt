package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.LoginRequest
import by.bsu.lsebackend.dto.LoginResponse
import by.bsu.lsebackend.dto.RefreshTokenRequest
import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.repository.UserRepositoryFacade
import by.bsu.lsebackend.security.TokenService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class AuthService(
    private val tokenService: TokenService,
    private val passwordEncoder: PasswordEncoder,
    private val userRepositoryFacade: UserRepositoryFacade,
) {

    @Transactional
    fun login(request: LoginRequest): Mono<LoginResponse> =
        userRepositoryFacade.findByEmail(request.email, request.userType)
            .filter { passwordEncoder.matches(request.password, it.password) }
            .flatMap(::updateRefreshToken)
            .switchIfEmpty(Mono.error(BadRequestException("Credentials are invalid")))

    @Transactional
    fun refreshToken(request: RefreshTokenRequest): Mono<LoginResponse> =
        tokenService.isValid(request.token.value)
            .flatMap {
                userRepositoryFacade.findByRefreshToken(request).flatMap(::updateRefreshToken)
                    .switchIfEmpty(Mono.error(BadRequestException("The user associated with the token is not found")))
            }
            .switchIfEmpty(Mono.error(BadRequestException("Refresh token is invalid")))

    private fun <T : BaseUser> updateRefreshToken(it: T): Mono<LoginResponse> {
        val refreshToken = tokenService.generateRefreshToken(it)
        val accessToken = tokenService.generateAccessToken(it)
        it.refreshToken = refreshToken.value
        return userRepositoryFacade.save(it).map {
            LoginResponse(accessToken, refreshToken, it.id, it.userType, it.userRole)
        }
    }
}