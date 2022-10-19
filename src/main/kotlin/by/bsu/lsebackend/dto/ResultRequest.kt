package by.bsu.lsebackend.dto

import by.bsu.lsebackend.entity.UserType
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

// todo: add validation like in QuizRequest

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultRequest(
    @JsonProperty("quizResult")
    @field:Valid
    val quizResultRequest: QuizResultRequest,
    @JsonProperty("userId")
    @field:NotBlank
    val userId: String,
    @JsonProperty("userType")
    @field:NotNull
    val userType: UserType,
)
