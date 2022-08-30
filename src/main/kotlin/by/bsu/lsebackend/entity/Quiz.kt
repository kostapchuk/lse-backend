package by.bsu.lsebackend.entity

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("quizzes")
class Quiz(
    @Id
    var id: String? = null,
    @Field("name")
    val name: String,
    @Field("questionsAndAnswers")
    val questionsAndAnswers: List<QuestionAndAnswers>
)
