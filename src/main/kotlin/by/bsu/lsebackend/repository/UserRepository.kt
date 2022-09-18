package by.bsu.lsebackend.repository

import by.bsu.lsebackend.entity.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UserRepository : ReactiveMongoRepository<User, String>
