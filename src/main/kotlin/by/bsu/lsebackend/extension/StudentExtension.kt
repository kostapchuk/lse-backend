package by.bsu.lsebackend.extension

import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.StudentResponse
import by.bsu.lsebackend.entity.Student

fun StudentRequest.toEntity() = Student(
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    password = this.password,
    group = this.group,
    faculty = this.faculty,
    role = this.role,
    course = this.course
)

fun Student.toResponse() = StudentResponse(
    id, lastName, email, faculty, group, course
)
