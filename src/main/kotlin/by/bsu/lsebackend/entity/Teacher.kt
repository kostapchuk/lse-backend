package by.bsu.lsebackend.entity

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document("teachers")
class Teacher(
    id: String = UUID.randomUUID().toString(),
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    role: String,
    @Field("degree")
    val degree: String,
    @Field("yearsOfExperience")
    val yearsOfExperience: String,
) : BaseUser(id, firstName, lastName, email, password, role)
