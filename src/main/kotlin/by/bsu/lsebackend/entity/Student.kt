package by.bsu.lsebackend.entity

import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

class Student(
    id: String = UUID.randomUUID().toString(),
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    userRole: UserRole,
    userType: UserType,
    faculty: String,
    @Field("group")
    val group: String,
    @Field("course")
    val course: Int,
) : BaseUser(id, firstName, lastName, email, password, userRole, userType, faculty)
