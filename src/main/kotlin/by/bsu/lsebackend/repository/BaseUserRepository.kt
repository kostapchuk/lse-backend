package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.BaseUser
import reactor.core.publisher.Mono

interface BaseUserRepository : GeneralBaseUserRepository<BaseUser, String> {
    fun existsByEmail(email: String): Mono<Boolean>
}
