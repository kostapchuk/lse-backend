package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
class RefreshTokenRequest(
    @JsonProperty("token")
    @field:NotNull
    val token: TokenDto,
    @JsonProperty("userType")
    @field:NotNull
    val userType: UserType,
)
