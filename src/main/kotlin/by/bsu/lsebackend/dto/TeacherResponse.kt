package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.Role
import com.fasterxml.jackson.annotation.JsonProperty

class TeacherResponse(
    firstName: String,
    lastName: String,
    email: String,
    faculty: String,
    @JsonProperty("yearsOfExperience") val yearsOfExperience: Int,
) : UserResponse(firstName, lastName, email, faculty, Role.ROLE_TEACHER.name) {

}
