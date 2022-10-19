package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.UUID

class QuizResultResponse(
    val id: String = UUID.randomUUID().toString(),
    val quizName: String,
    val firstName: String,
    val lastName: String,
    val group: String,
    val faculty: String,
    val email: String,
    val score: Int,
    val maxScore: Int,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdDate: LocalDateTime,
)
