package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.UserType

class DeleteUserRequest(
    val userId: String,
    val userType: UserType,
)
