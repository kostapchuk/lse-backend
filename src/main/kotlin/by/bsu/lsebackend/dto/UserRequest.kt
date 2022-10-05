package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.Role
import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

// todo add validation

@JsonIgnoreProperties(ignoreUnknown = true)
open class UserRequest(
    @JsonProperty("firstName")
    val firstName: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("email")
    val email: String,

    @JsonProperty("password")
    val password: String,

    @JsonProperty("faculty")
    val faculty: String,

    @JsonProperty("role")
    val role: Role,

    @JsonProperty("userType")
    val userType: UserType,
)
