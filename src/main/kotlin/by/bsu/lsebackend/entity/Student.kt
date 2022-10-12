package by.bsu.lsebackend.entity

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document("users")
class Student(
    id: String = UUID.randomUUID().toString(),
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    role: Role,
    userType: UserType,
    faculty: String,
    @Field("group")
    val group: String,
    @Field("course")
    val course: Int,
) : BaseUser(id, firstName, lastName, email, password, role, userType, faculty)
