package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.Quiz
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface QuizRepository : ReactiveMongoRepository<Quiz, String>
