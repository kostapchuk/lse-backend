package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.Teacher
import by.bsu.lsebackend.entity.UserRole
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
    @JsonProperty("yearsOfExperience")
    val yearsOfExperience: Int,
) : UserRequest<Teacher>(firstName, lastName, email, password, faculty, UserRole.ROLE_TEACHER, UserType.TEACHER) {
    override fun toEntity(): Teacher = Teacher(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        faculty = this.faculty,
        userRole = this.userRole,
        userType = this.userType,
        yearsOfExperience = this.yearsOfExperience,
    )
}
