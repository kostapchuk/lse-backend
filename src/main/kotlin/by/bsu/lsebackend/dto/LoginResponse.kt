package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.UserRole
import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonProperty

class LoginResponse(
    @JsonProperty("accessToken")
    val accessToken: Token,
    @JsonProperty("refreshToken")
    val refreshToken: Token,
    @JsonProperty("id")
    val id: String,
    @JsonProperty("userType")
    val userType: UserType,
    @JsonProperty("userRole")
    val userRole: UserRole,
)
