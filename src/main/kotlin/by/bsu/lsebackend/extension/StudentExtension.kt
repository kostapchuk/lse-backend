package by.bsu.lsebackend.extension

import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.dto.StudentRequest
import by.bsu.lsebackend.dto.TeacherRequest
import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.entity.Student
import by.bsu.lsebackend.entity.Teacher

fun StudentRequest.toEntity(): Student = Student(
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    password = this.password,
    group = this.group,
    faculty = this.faculty,
    course = this.course,
    role = this.role,
    userType = this.userType
)

fun <T : BaseUser> T.toResponse(): RegisterResponse = RegisterResponse(
    firstName, lastName, email,
)

fun TeacherRequest.toEntity(): Teacher = Teacher(
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    password = this.password,
    yearsOfExperience = this.yearsOfExperience,
    faculty = this.faculty,
    role = this.role,
    userType = this.userType
)

