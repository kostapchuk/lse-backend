package by.bsu.lsebackend.entity

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document("users")
class Teacher(
    id: String = UUID.randomUUID().toString(),
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    userRole: UserRole,
    userType: UserType,
    faculty: String,
    @Field("yearsOfExperience")
    val yearsOfExperience: Int,
) : BaseUser(id, firstName, lastName, email, password, userRole, userType, faculty)
