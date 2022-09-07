package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

// TODO add validation

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResultRequest(
    @JsonProperty("quizResult") val quizResultRequest: QuizResultRequest,
    @JsonProperty("user") val userResultRequest: UserResultRequest,
)
