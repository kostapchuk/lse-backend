package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
class QuizResult(
    @field:NotNull(message = "Quiz id should present")
    @field:NotEmpty(message = "Quiz id should present")
    val id: String,
    val questionAndAnswersResult: List<QuestionAndAnswersRequest>,
)
