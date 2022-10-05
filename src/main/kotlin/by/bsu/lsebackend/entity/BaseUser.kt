package by.bsu.lsebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

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
    val role: Role,

    @Field("userType")
    val userType: UserType,

    @Field("refreshToken")
    var refreshToken: String? = null,
)
