package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.Role
import by.bsu.lsebackend.entity.UserType

class LoginResponse(
    val accessToken: String,
    val refreshToken: String?,
    val id: String,
    userType: UserType,
    userRole: Role,
)
