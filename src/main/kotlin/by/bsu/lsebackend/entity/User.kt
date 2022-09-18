package by.bsu.lsebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document("users")
data class User(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Field("firstName")
    val firstName: String,

    @Field("lastName")
    val lastName: String,

    @Field("email")
    val email: String,

    @Field("password")
    val password: String,

    @Field("group")
    val group: String,

    @Field("faculty")
    val faculty: String,

    @Field("role")
    val role: String, // create converter https://www.mongodb.com/community/forums/t/cannot-store-java-enum-values-in-mongodb/99719/3
)
