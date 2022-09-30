package by.bsu.lsebackend.service

import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.StudentResponse
import by.bsu.lsebackend.exception.BadRequestException
import by.bsu.lsebackend.exception.ResourceNotFoundException
import by.bsu.lsebackend.extension.toEntity
import by.bsu.lsebackend.extension.toResponse
import by.bsu.lsebackend.repository.StudentRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class UserService(private val studentRepository: StudentRepository) {

    fun register(studentRequest: StudentRequest) = findByEmailOrError(studentRequest.email)
        .switchIfEmpty(
            studentRepository.insert(studentRequest.toEntity())
                .map { it.toResponse() }
        )

    fun findById(id: String): Mono<StudentResponse> = studentRepository.findById(id)
        .map { it.toResponse() }
        .switchIfEmpty(
            Mono.error(ResourceNotFoundException("User with $id was not found"))
        )

    fun findAll(): Flux<StudentResponse> = studentRepository.findAll()
        .map { it.toResponse() }

    fun updateById(id: String, studentRequest: StudentRequest) = studentRepository.findById(id)
        .flatMap {
            studentRepository.save(studentRequest.toEntity()).map { it.toResponse() }
        }.switchIfEmpty(
            Mono.error(
                BadRequestException("User with $id does not exist")
            )
        )

    fun deleteById(id: String): Mono<Void> = findById(id).flatMap { studentRepository.deleteById(id) }

    private fun findByEmailOrError(email: String) =
        studentRepository.findByEmail(email)
            .flatMap {
                Mono.error<StudentResponse>(
                    BadRequestException("User with $email already exists")
                )
            }
}
