package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
class RefreshTokenRequest(
    @JsonProperty("refreshToken")
    @field:NotBlank
    val refreshToken: String,
    @JsonProperty("userType")
    @field:NotBlank
    val userType: UserType,
)
