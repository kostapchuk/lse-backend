package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class QuizResponse(
    @JsonProperty("quizId")
    val id: String,
    @JsonProperty("quizName")
    val name: String,
    @JsonProperty("quizItems")
    val items: List<QuizItemResponse>,
    @JsonProperty("createdDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdDate: LocalDateTime,
) {
    class QuizItemResponse(
        @JsonProperty("question")
        val question: QuestionResponse,
        @JsonProperty("answers")
        val answers: List<AnswerResponse>,
    ) {

        class QuestionResponse(
            @JsonProperty("id")
            val id: String,
            @JsonProperty("text")
            val text: String,
            @JsonProperty("multipleChoice")
            val multipleChoice: Boolean,
        )

        class AnswerResponse(
            @JsonProperty("id")
            val id: String,
            @JsonProperty("text")
            val text: String,
        )
    }
}
