package by.bsu.lsebackend.mapper

import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.entity.BaseUser
import org.springframework.stereotype.Component

@Component
class BaseUserMapper {

    fun <T : BaseUser> toResponse(user: T): RegisterResponse = RegisterResponse(
        user.firstName, user.lastName, user.email,
    )
}
