package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.UUID

class QuizResultResponse(
    val id: String = UUID.randomUUID().toString(),
    val quizName: String,
    val score: Int,
    val maxScore: Int,
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    val createdDate: LocalDateTime,
)
