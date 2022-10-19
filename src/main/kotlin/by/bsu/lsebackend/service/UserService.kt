package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.DeleteUserRequest
import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.dto.UserRequest
import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.extension.toResponse
import by.bsu.lsebackend.repository.UserRepositoryFacade
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
class UserService(
    private val userRepositoryFacade: UserRepositoryFacade,
    private val passwordEncoder: PasswordEncoder,
) {
    @Transactional
    fun <T : UserRequest<out BaseUser>> register(request: T): Mono<RegisterResponse> =
        userRepositoryFacade.existsByEmail(request.email)
            .flatMap<RegisterResponse> {
                Mono.error(BadRequestException("User with ${request.email} already exists"))
            }.switchIfEmpty(
                Mono.just(request).map {
                    it.toEntity()
                }.flatMap {
                    it.password = passwordEncoder.encode(it.password)
                    userRepositoryFacade.save(it)
                }.map { it.toResponse() }
            )

    fun delete(request: DeleteUserRequest): Mono<Void> =
        userRepositoryFacade.delete(request)
}
