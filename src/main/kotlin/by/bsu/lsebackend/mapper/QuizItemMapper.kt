package by.bsu.lsebackend.mapper

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import org.springframework.stereotype.Component

@Component
class QuizItemMapper(private val answerMapper: AnswerMapper, private val questionMapper: QuestionMapper) {

    fun toResponse(quizItem: Quiz.QuizItem): QuizResponse.QuizItemResponse = QuizResponse.QuizItemResponse(
        questionMapper.toResponse(quizItem.question),
        quizItem.answers.map { answerMapper.toResponse(it) }
    )

    fun toEntity(quizItemRequest: QuizRequest.QuizItemRequest): Quiz.QuizItem = Quiz.QuizItem(
        questionMapper.toEntity(quizItemRequest.question),
        quizItemRequest.answers.map { answerMapper.toEntity(it) }
    )
}
