package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.Student
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface StudentRepository : ReactiveMongoRepository<Student, String> {

    fun findByEmail(email: String): Mono<Student>
}
