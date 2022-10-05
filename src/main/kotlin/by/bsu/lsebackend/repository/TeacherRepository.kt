package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.Teacher
import by.bsu.lsebackend.entity.UserType
import by.bsu.lsebackend.entity.UserType.TEACHER
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface TeacherRepository : ReactiveMongoRepository<Teacher, String> {
    fun findByEmailAndUserType(email: String, userType: UserType = TEACHER): Mono<Teacher>
    fun findByRefreshTokenAndUserType(refreshToken: String, userType: UserType = TEACHER): Mono<Teacher>
}
