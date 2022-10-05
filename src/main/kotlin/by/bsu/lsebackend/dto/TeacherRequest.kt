package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.Role
import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

// todo add validation

@JsonIgnoreProperties(ignoreUnknown = true)
class TeacherRequest(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    faculty: String,
    role: Role,
    @JsonProperty("yearsOfExperience")
    val yearsOfExperience: Int,
    userType: UserType,
) : UserRequest(firstName, lastName, email, password, faculty, role, userType)
