package by.bsu.lsebackend.extension

import by.bsu.lsebackend.dto.UserRequest
import by.bsu.lsebackend.dto.UserResponse
import by.bsu.lsebackend.entity.User

fun UserRequest.toEntity() = User(
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    password = this.password,
    group = this.group,
    faculty = this.faculty,
    role = this.role
)

fun User.toResponse() = UserResponse()
