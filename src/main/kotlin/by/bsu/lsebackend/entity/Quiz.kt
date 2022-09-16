package by.bsu.lsebackend.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.UUID

@Document("quizzes")
data class Quiz(
    @Id
    val id: String = UUID.randomUUID().toString(),
    @Field("name")
    val name: String,
    @Field("items")
    val items: List<QuizItem>,
    @Field("maxScore")
    val maxScore: Int,
) {
    class QuizItem(
        val question: Question,
        val answers: List<Answer>,
    ) {

        class Question(
            val id: String = UUID.randomUUID().toString(),
            val text: String,
            val cost: Int,
            val multipleChoice: Boolean = false,
        )

        class Answer(
            val id: String = UUID.randomUUID().toString(),
            val text: String,
            val correct: Boolean = false,
        )
    }
}
