package by.bsu.lsebackend.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import java.util.UUID

@Document("results")
data class QuizResult(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @Field("quizName")
    val quizName: String,
    @Field("score")
    val score: Int,
    @Field("maxScore")
    val maxScore: Int,
    @Field("email")
    val email: String,
    @Field("createdDate")
    @CreatedDate
    val createdDate: LocalDateTime,
)
