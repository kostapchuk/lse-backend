package by.bsu.lsebackend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class ResourceNotFoundException(override val message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.BAD_REQUEST)
data class BadRequestException(override val message: String) : RuntimeException(message)