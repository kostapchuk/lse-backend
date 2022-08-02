package by.bsu.lsebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("users")
data class User(
     @Id
     var id: String? = null,
     @Field("firstName")
     val firstName: String,
     @Field("age")
     val age: Int
)
