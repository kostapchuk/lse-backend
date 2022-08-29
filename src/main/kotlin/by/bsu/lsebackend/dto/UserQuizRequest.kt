package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
class UserQuizRequest(
    @field:NotEmpty(message = "First name should present")
    @field:NotNull(message = "First name should present")
    val firstName: String,
    @field:NotEmpty(message = "Last name should present")
    @field:NotNull(message = "Last name should present")
    val lastName: String,
    @field:Email(message = "Email should present")
    val email: String,
    @field:NotEmpty(message = "Group should present")
    @field:NotNull(message = "Group should present")
    val group: String,
    @field:NotEmpty(message = "Faculty should present")
    @field:NotNull(message = "Faculty should present")
    val faculty: String,
)
