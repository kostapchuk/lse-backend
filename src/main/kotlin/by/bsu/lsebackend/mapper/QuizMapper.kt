package by.bsu.lsebackend.mapper

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class QuizMapper(private val quizItemMapper: QuizItemMapper,) {

    fun toResponse(quiz: Quiz): QuizResponse = QuizResponse(
        quiz.id,
        quiz.name,
        quiz.items.map { quizItemMapper.toResponse(it) },
        quiz.createdDate,
    )

    fun toEntity(quizRequest: QuizRequest): Quiz = Quiz(
        name = quizRequest.name,
        items = quizRequest.items.map { quizItemMapper.toEntity(it) },
        maxScore = quizRequest.maxScore,
        createdDate = LocalDateTime.now()
    )
}
