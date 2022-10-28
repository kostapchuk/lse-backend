package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
class LoginRequest(
    @JsonProperty("email")
    @field:Email
    @field:NotBlank
    val email: String,
    @JsonProperty("password")
    @field:NotBlank
    val password: String,
    @JsonProperty("userType")
    @field:NotNull
    val userType: UserType,
)
