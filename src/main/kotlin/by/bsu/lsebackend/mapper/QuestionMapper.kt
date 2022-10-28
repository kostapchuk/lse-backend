package by.bsu.lsebackend.mapper

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import org.springframework.stereotype.Component

@Component
class QuestionMapper {

    fun toResponse(question: Quiz.QuizItem.Question): QuizResponse.QuizItemResponse.QuestionResponse =
        QuizResponse.QuizItemResponse.QuestionResponse(
            question.id,
            question.text,
            question.multipleChoice
        )

    fun toEntity(questionRequest: QuizRequest.QuizItemRequest.QuestionRequest): Quiz.QuizItem.Question =
        Quiz.QuizItem.Question(
            text = questionRequest.text,
            multipleChoice = questionRequest.multipleChoice,
            cost = questionRequest.cost
        )
}
