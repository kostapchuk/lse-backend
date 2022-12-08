package by.bsu.lsebackend.dto

import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ErrorResponse(
    val status: HttpStatus,
    val message: String,
    val datetime: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:SS"))
        .toString(),
)