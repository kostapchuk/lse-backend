package by.bsu.lsebackend.extension

import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.StudentResponse
import by.bsu.lsebackend.dto.TeacherRequest
import by.bsu.lsebackend.dto.TeacherResponse
import by.bsu.lsebackend.entity.Student
import by.bsu.lsebackend.entity.Teacher

fun StudentRequest.toEntity() = Student(
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    password = this.password,
    group = this.group,
    faculty = this.faculty,
    course = this.course,
    role = this.role
)

fun Student.toResponse() = StudentResponse(
    firstName, lastName, email, faculty, group, course,
)

fun TeacherRequest.toEntity() = Teacher(
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    password = this.password,
    yearsOfExperience = this.yearsOfExperience,
    faculty = this.faculty,
    role = this.role,
    userType = this.userType
)

fun Teacher.toResponse() = TeacherResponse(
    firstName, lastName, email, faculty, yearsOfExperience
)
