package by.bsu.lsebackend.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID

class Answer(
    var id: String = UUID.randomUUID().toString(),
    val text: String,
    @JsonIgnore
    val correct: Boolean = false
)
