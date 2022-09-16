package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonProperty

class QuizResponse(
    @JsonProperty("quizId")
    val id: String,
    @JsonProperty("quizName")
    val name: String,
    @JsonProperty("quizItems")
    val items: List<QuizItemResponse>,
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
