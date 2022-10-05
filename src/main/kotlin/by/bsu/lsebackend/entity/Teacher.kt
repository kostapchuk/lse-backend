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
    role: Role,
    userType: UserType,
    @Field("yearsOfExperience")
    val yearsOfExperience: Int,
    @Field("faculty")
    val faculty: String,
) : BaseUser(id, firstName, lastName, email, password, role, userType)
