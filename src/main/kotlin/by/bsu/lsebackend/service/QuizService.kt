package by.bsu.lsebackend.service

import by.bsu.lsebackend.entity.Quiz
import by.bsu.lsebackend.repository.QuizRepository
import org.springframework.stereotype.Service

@Service
class QuizService(private val quizRepository: QuizRepository) {

    fun findAll() = quizRepository.findAll()

    fun create (quiz: Quiz) = quizRepository.save(quiz)
}
