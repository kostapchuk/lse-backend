package by.bsu.lsebackend.mapper

import by.bsu.lsebackend.dto.UserRequest
import by.bsu.lsebackend.dto.UserResponse
import by.bsu.lsebackend.entity.User

fun UserRequest.toEntity(): User =
    User(
        firstName = this.firstName,
        age = this.age
    )

fun User.toResponse(): UserResponse =
    UserResponse(
        id = this.id,
        firstName = this.firstName,
        age = this.age
    )