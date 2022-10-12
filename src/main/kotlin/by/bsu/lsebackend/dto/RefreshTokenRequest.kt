package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.UserType

class RefreshTokenRequest(
    val refreshToken: String,
    val userType: UserType,
)
