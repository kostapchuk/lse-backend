package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

// TODO add validation

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultRequest(
    @JsonProperty("quizResult")
    @field:Valid
    val quizResultRequest: QuizResultRequest,
    @JsonProperty("user")
    @field:Valid
    val userResultRequest: UserResultRequest,
)