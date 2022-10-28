package by.bsu.lsebackend.mapper

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import org.springframework.stereotype.Component

@Component
class AnswerMapper {

    fun toResponse(answer: Quiz.QuizItem.Answer): QuizResponse.QuizItemResponse.AnswerResponse =
        QuizResponse.QuizItemResponse.AnswerResponse(
            answer.id,
            answer.text
        )

    fun toEntity(answerRequest: QuizRequest.QuizItemRequest.AnswerRequest): Quiz.QuizItem.Answer =
        Quiz.QuizItem.Answer(
            text = answerRequest.text,
            correct = answerRequest.correct
        )
}
