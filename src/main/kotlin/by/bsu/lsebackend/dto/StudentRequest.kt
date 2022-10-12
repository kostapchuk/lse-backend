package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.Role
import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

// todo add validation

@JsonIgnoreProperties(ignoreUnknown = true)
class StudentRequest(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    faculty: String,
    role: Role,
    @JsonProperty("group")
    @field:NotBlank
    val group: String,
    @JsonProperty("course")
    @field:NotNull
    @field:Min(1)
    @field:Max(5)
    val course: Int,
    userType: UserType,
) : UserRequest(firstName, lastName, email, password, faculty, role, userType)
