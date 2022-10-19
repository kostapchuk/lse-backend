package by.bsu.lsebackend.extension

import by.bsu.lsebackend.dto.QuizRequest
import by.bsu.lsebackend.dto.QuizResponse
import by.bsu.lsebackend.entity.Quiz
import java.time.LocalDateTime

fun Quiz.toResponse(): QuizResponse = QuizResponse(
    this.id,
    this.name,
    this.items.map { it.toResponse() },
    this.createdDate,
)

fun Quiz.QuizItem.toResponse(): QuizResponse.QuizItemResponse = QuizResponse.QuizItemResponse(
    this.question.toResponse(),
    this.answers.map { it.toResponse() }
)

fun Quiz.QuizItem.Answer.toResponse(): QuizResponse.QuizItemResponse.AnswerResponse =
    QuizResponse.QuizItemResponse.AnswerResponse(
        this.id,
        this.text
    )

fun Quiz.QuizItem.Question.toResponse(): QuizResponse.QuizItemResponse.QuestionResponse =
    QuizResponse.QuizItemResponse.QuestionResponse(
        this.id,
        this.text,
        this.multipleChoice
    )

fun QuizRequest.toEntity(): Quiz = Quiz(
    name = this.name,
    items = this.items.map { it.toEntity() },
    maxScore = this.maxScore,
    createdDate = LocalDateTime.now()
)

fun QuizRequest.QuizItemRequest.toEntity(): Quiz.QuizItem = Quiz.QuizItem(
    this.question.toEntity(),
    this.answers.map { it.toEntity() }
)

fun QuizRequest.QuizItemRequest.AnswerRequest.toEntity(): Quiz.QuizItem.Answer = Quiz.QuizItem.Answer(
    text = this.text,
    correct = this.correct
)

fun QuizRequest.QuizItemRequest.QuestionRequest.toEntity(): Quiz.QuizItem.Question = Quiz.QuizItem.Question(
    text = this.text,
    multipleChoice = this.multipleChoice,
    cost = this.cost
)
