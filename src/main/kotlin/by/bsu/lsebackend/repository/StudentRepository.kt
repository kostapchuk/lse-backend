package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.Student
import by.bsu.lsebackend.entity.UserType
import by.bsu.lsebackend.entity.UserType.STUDENT
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface StudentRepository : ReactiveMongoRepository<Student, String> {
    fun findByEmailAndUserType(email: String, userType: UserType = STUDENT): Mono<Student>
    fun findByRefreshTokenAndUserType(refreshToken: String, userType: UserType = STUDENT): Mono<Student>
}
