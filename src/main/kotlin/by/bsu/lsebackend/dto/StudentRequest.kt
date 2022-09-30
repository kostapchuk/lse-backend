package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

// todo add validation

@JsonIgnoreProperties(ignoreUnknown = true)
data class StudentRequest(
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

    @JsonProperty("group")
    val group: String,

    @JsonProperty("course")
    val course: Int,

    @JsonProperty("role")
    val role: String,
)
