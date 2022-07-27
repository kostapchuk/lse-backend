package by.bsu.lsebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("user")
data class User(
     @Id
     var id: String? = null,
     val firstName: String,
     val age: Int
)
