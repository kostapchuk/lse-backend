package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class QuizResultRequest(
    val quizResult: QuizResult,
    val userQuizRequest: UserQuizRequest
)
