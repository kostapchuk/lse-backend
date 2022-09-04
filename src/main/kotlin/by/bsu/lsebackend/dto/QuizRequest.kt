package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class QuizRequest(
    @JsonProperty("quizName") val name: String,
    @JsonProperty("quizItems") val items: List<QuizItemRequest>,
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    class QuizItemRequest(
        @JsonProperty("question") val question: QuestionRequest,
        @JsonProperty("answers") val answers: List<AnswerRequest>,
    ) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        class QuestionRequest(
            @JsonProperty("text") val text: String,
            @JsonProperty("multipleChoice") val multipleChoice: Boolean,
            @JsonProperty("cost") val cost: Int,
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        class AnswerRequest(
            @JsonProperty("text") val text: String,
            @JsonProperty("correct") val correct: Boolean = false,
        )
    }
}
