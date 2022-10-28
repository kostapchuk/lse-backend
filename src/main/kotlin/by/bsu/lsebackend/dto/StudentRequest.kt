package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.Student
import by.bsu.lsebackend.entity.UserRole
import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
class StudentRequest(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    faculty: String,
    @JsonProperty("group")
    @field:NotBlank
    val group: String,
    @JsonProperty("course")
    @field:NotNull
    @field:Min(1)
    @field:Max(5)
    val course: Int,
) : UserRequest<Student>(firstName, lastName, email, password, faculty, UserRole.ROLE_STUDENT, UserType.STUDENT) {
    override fun toEntity(): Student = Student(
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = this.password,
        group = this.group,
        faculty = this.faculty,
        course = this.course,
        userRole = this.userRole,
        userType = this.userType
    )
}
