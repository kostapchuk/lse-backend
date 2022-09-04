package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserResultRequest(
    @field:NotEmpty(message = "First name should present")
    @field:NotNull(message = "First name should present")
    @JsonProperty("firstName") val firstName: String,

    @field:NotEmpty(message = "Last name should present")
    @field:NotNull(message = "Last name should present")
    @JsonProperty("lastName") val lastName: String,

    @field:Email(message = "Email should present")
    @JsonProperty("email") val email: String,

    @field:NotEmpty(message = "Group should present")
    @field:NotNull(message = "Group should present")
    @JsonProperty("group") val group: String,

    @field:NotEmpty(message = "Faculty should present")
    @field:NotNull(message = "Faculty should present")
    @JsonProperty("faculty") val faculty: String,
)
