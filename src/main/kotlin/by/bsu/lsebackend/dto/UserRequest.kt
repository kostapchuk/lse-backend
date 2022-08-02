package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserRequest(
    @field:NotEmpty(message = "First name should present")
    @field:NotNull(message = "First name should present")
    val firstName: String,
    @field:NotNull
    @field:Min(value = 18)
    @field:Max(value = 150)
    val age: Int
)
