package by.bsu.lsebackend.extension

import by.bsu.lsebackend.dto.RegisterResponse
import by.bsu.lsebackend.entity.BaseUser

fun <T : BaseUser> T.toResponse(): RegisterResponse = RegisterResponse(
    firstName, lastName, email,
)
