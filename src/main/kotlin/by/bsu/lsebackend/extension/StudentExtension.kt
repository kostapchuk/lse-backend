package by.bsu.lsebackend.extension

import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.StudentResponse
import by.bsu.lsebackend.entity.Role
import by.bsu.lsebackend.entity.Student

fun StudentRequest.toEntity() = Student(
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    password = this.password,
    group = this.group,
    faculty = this.faculty,
    role = Role.valueOf(this.role),
    course = this.course
)

fun Student.toResponse() = StudentResponse(
    firstName, lastName, email, faculty, group, course
)
