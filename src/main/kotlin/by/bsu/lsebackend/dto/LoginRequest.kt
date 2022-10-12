package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.UserType

class LoginRequest(
    val email: String,
    val password: String,
    val userType: UserType,
)
