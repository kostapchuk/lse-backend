package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

// todo: investigate how to validate integers (to allow only integers and throw validation exception for numbers with decimal part)
// todo: investigate how to validate booleans (to allow only true/false and do not cast 12 as true)

@JsonIgnoreProperties(ignoreUnknown = true)
class QuizRequest(
    @JsonProperty("quizName", required = true)
    @field:NotEmpty(message = "Quiz name should present")
    @field:Size(min = 1, max = 255, message = "Quiz name length should not be less than {min} and greater than {max}")
    val name: String,
    @JsonProperty("quizItems", required = true)
    @field:NotEmpty(message = "Quiz items should present")
    @field:Valid
    val items: List<QuizItemRequest>,
    @JsonProperty("maxScore", required = true)
    @field:Min(value = 1, message = "Max score should not be less than {value}")
    @field:Max(value = Int.MAX_VALUE.toLong(), message = "Max score should not be greater than {value}")
    @field:NotNull(message = "Max score should present")
    val maxScore: Int,
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    class QuizItemRequest(
        @JsonProperty("question", required = true)
        @field:Valid
        @field:NotNull(message = "Question should present")
        val question: QuestionRequest,
        @JsonProperty("answers", required = true)
        @field:Valid
        @field:NotEmpty(message = "Answers should present")
        val answers: List<AnswerRequest>,
    ) {

        @JsonIgnoreProperties(ignoreUnknown = true)
        class QuestionRequest(
            @JsonProperty("text", required = true)
            @field:NotEmpty(message = "Question's text should present")
            @field:Size(
                min = 1,
                max = 255,
                message = "Quiz name length should not be less than {min} and greater than {max}"
            )
            val text: String,
            @JsonProperty("multipleChoice")
            @field:NotNull
            val multipleChoice: Boolean = false,
            @JsonProperty("cost", required = true)
            @field:Min(value = 1, message = "Cost should not be less than {value}")
            @field:Max(value = Int.MAX_VALUE.toLong(), message = "Cost should not be greater than {value}")
            @field:NotNull(message = "Cost should present")
            val cost: Int,
        )

        @JsonIgnoreProperties(ignoreUnknown = true)
        class AnswerRequest(
            @JsonProperty("text", required = true)
            @field:NotEmpty(message = "Answer's text should present")
            @field:Size(
                min = 1,
                max = 255,
                message = "Quiz name length should not be less than {min} and greater than {max}"
            )
            val text: String,
            @JsonProperty("correct")
            @field:NotNull(message = "Correctness value should present")
            val correct: Boolean = false,
        )
    }
}
