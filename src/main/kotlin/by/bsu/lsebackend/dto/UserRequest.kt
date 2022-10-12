package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.Role
import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

// todo add validation

@JsonIgnoreProperties(ignoreUnknown = true)
open class UserRequest(
    @JsonProperty("firstName")
    @field:NotBlank
    val firstName: String,
    @JsonProperty("lastName")
    @field:NotBlank
    val lastName: String,
    @JsonProperty("email")
    @field:NotBlank
    @field:Email
    val email: String,
    @JsonProperty("password")
    @field:NotBlank
    val password: String,
    @JsonProperty("faculty")
    @field:NotBlank
    val faculty: String,
    @JsonProperty("role")
    @field:NotNull
    val role: Role,
    @JsonProperty("userType")
    @field:NotNull
    val userType: UserType,
)
