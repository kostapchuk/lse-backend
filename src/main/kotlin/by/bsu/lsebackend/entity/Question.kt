package by.bsu.lsebackend.entity

import java.util.UUID

class Question(
    var id: String = UUID.randomUUID().toString(),
    val text: String,
    val multipleChoice: Boolean
)
