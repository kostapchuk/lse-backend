package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.entity.UserType
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.NoRepositoryBean
import reactor.core.publisher.Mono
import java.io.Serializable

@NoRepositoryBean
interface GeneralBaseUserRepository<E : BaseUser, ID : Serializable> : ReactiveMongoRepository<E, ID> {

    fun findByEmailAndUserType(email: String, userType: UserType): Mono<E>
    fun findByRefreshTokenAndUserType(refreshToken: String, userType: UserType): Mono<E>
    fun findByIdAndUserType(id: ID, userType: UserType): Mono<E>
}
