package by.bsu.lsebackend.exception

import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(NOT_FOUND)
data class ResourceNotFoundException(override val message: String) : RuntimeException(message)

@ResponseStatus(BAD_REQUEST)
data class BadRequestException(override val message: String) : RuntimeException(message)
