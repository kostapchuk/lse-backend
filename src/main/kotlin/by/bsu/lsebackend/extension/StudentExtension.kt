package by.bsu.lsebackend.extension

import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.entity.BaseUser

// todo create mapper
fun <T : BaseUser> T.toResponse(): RegisterResponse = RegisterResponse(
    firstName, lastName, email,
)
