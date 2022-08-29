package by.bsu.lsebackend.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class QuestionAndAnswersRequest (
    @field:NotNull(message = "Question id should present")
    @field:NotEmpty(message = "Question id should present")
    val questionId: String,
    val answerIds: List<String>
)
