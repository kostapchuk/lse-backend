package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserQuizRequest(
    @field:NotBlank(message = "First name should present")
    @JsonProperty("firstName")
    val firstName: String,

    @field:NotBlank(message = "Last name should present")
    @JsonProperty("lastName")
    val lastName: String,

    @field:Email(message = "Email should present")
    @JsonProperty("email")
    val email: String,

    @field:NotBlank(message = "Group should present")
    @JsonProperty("group")
    val group: String,

    @field:NotBlank(message = "Faculty should present")
    @JsonProperty("faculty")
    val faculty: String,
)
