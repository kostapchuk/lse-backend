package by.bsu.lsebackend.entity

import java.util.UUID

class Answer(
    var id: String = UUID.randomUUID().toString(),
    val text: String,
    val correct: Boolean = false
)
