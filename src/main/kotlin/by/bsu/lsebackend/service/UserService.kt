package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.dto.UserRequest
import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.extension.toResponse
import by.bsu.lsebackend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    fun <T : UserRequest<out BaseUser>> register(request: T): Mono<RegisterResponse> =
        userRepository.existsByEmail(request.email).filter { it == true }
            .flatMap<RegisterResponse> {
                Mono.error(BadRequestException("User with email ${request.email} already exists"))
            }.switchIfEmpty(
                Mono.just(request).map {
                    it.toEntity()
                }.flatMap {
                    it.password = passwordEncoder.encode(it.password)
                    userRepository.save(it)
                }.map { it.toResponse() }
            )

    fun deleteById(userId: String): Mono<Void> =
        userRepository.deleteById(userId)
}
