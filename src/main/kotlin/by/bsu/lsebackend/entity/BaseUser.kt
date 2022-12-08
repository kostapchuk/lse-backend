package by.bsu.lsebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document("users")
open class BaseUser(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @Field("firstName")
    val firstName: String,

    @Field("lastName")
    val lastName: String,

    @Field("email")
    val email: String,

    @Field("password")
    var password: String,

    @Field("role")
    val userRole: UserRole,

    @Field("userType")
    val userType: UserType,

    @Field("faculty")
    val faculty: String,

    @Field("refreshToken")
    var refreshToken: String? = null,
)
