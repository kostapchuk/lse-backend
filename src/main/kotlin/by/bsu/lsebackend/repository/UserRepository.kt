package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.entity.UserType
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveMongoRepository<BaseUser, String> {

    fun existsByEmail(email: String): Mono<Boolean>
    fun findByEmailAndUserType(email: String, userType: UserType): Mono<BaseUser>
    fun findByRefreshTokenAndUserType(refreshToken: String, userType: UserType): Mono<BaseUser>
    fun findByIdAndUserType(id: String, userType: UserType): Mono<BaseUser>
}
