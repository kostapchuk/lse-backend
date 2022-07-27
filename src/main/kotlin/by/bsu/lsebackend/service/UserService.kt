package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.UserRequest
import by.bsu.lsebackend.dto.UserResponse
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.exception.ResourceNotFoundException
import by.bsu.lsebackend.mapper.toEntity
import by.bsu.lsebackend.mapper.toResponse
import by.bsu.lsebackend.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(private val userRepository: UserRepository) {

    fun findById(id: String): Mono<UserResponse> = userRepository.findById(id)
        .map { it.toResponse() }
        .switchIfEmpty(
            Mono.error(ResourceNotFoundException("User with $id was not found"))
        )

    fun findAll(): Flux<UserResponse> = userRepository.findAll()
        .map { it.toResponse() }

    fun save(userRequest: UserRequest) = findByFirstNameOrError(userRequest.firstName)
        .switchIfEmpty(
            userRepository.save(userRequest.toEntity())
                .map { it.toResponse() }
        )


    fun updateById(id: String, userRequest: UserRequest): Mono<UserResponse> = userRepository.findById(id)
        .flatMap { foundUser ->
            findByFirstNameOrError(foundUser.firstName)
                .switchIfEmpty(
                    userRepository.save(foundUser)
                        .map { it.toResponse() }
                )
        }

    fun deleteById(id: String): Mono<Void> = findById(id).flatMap { userRepository.deleteById(id) }

    private fun findByFirstNameOrError(firstName: String) =
        userRepository.findByFirstName(firstName)
            .flatMap {
                Mono.error<UserResponse>(
                    BadRequestException("User with $firstName already exists")
                )
            }
}
