package by.bsu.lsebackend.mapper

import by.bsu.lsebackend.dto.QuizResultResponse
import by.bsu.lsebackend.entity.QuizResult
import org.springframework.stereotype.Component

@Component
class QuizResultMapper {

    fun toResponse(quizResult: QuizResult): QuizResultResponse = QuizResultResponse(
        quizName = quizResult.quizName,
        maxScore = quizResult.maxScore,
        score = quizResult.score,
        createdDate = quizResult.createdDate,
        email = quizResult.email,
    )
}
