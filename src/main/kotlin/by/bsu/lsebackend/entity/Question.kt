package by.bsu.lsebackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID

class Question(
    var id: String = UUID.randomUUID().toString(),
    val text: String,
    val multipleChoice: Boolean,
    @JsonIgnore
    val cost: Int
)
