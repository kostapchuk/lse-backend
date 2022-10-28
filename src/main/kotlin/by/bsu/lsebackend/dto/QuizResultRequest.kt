package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
data class QuizResultRequest(
    @field:NotBlank(message = "Quiz id should present")
    @JsonProperty("quizId")
    val quizId: String,

    @field:NotNull(message = "Quiz items should present")
    @field:Valid
    @JsonProperty("items")
    val items: List<QuizItemRequest>,
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class QuizItemRequest(
        @field:NotBlank(message = "Question id should present")
        @JsonProperty("questionId")
        val questionId: String,

        @field:NotNull(message = "Answers should present")
        @JsonProperty("answerIds")
        val answerIds: List<String>,
    )
}
