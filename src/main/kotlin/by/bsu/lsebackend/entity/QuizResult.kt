package by.bsu.lsebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document("results")
data class QuizResult(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @Field("quizName")
    val quizName: String,
//    @Field("attempt")
//    val attempt: Int,
    @Field("firstName")
    val firstName: String,
    @Field("lastName")
    val lastName: String,
    @Field("group")
    val group: String,
    @Field("faculty")
    val faculty: String,
    @Field("email")
    val email: String,
    @Field("score")
    val score: Int,
    @Field("maxScore")
    val maxScore: Int,
)
