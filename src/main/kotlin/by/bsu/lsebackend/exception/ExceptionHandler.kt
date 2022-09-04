package by.bsu.lsebackend.exception

import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestControllerAdvice
//@Slf4j
class ExceptionHandlers {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun serverExceptionHandler(ex: Exception): String? {
//        .error(ex.message, ex)
        return ex.message
    }

    @ExceptionHandler(DecodingException::class)
    @ResponseStatus(BAD_REQUEST)
    fun decodingExceptionHandler(ex: DecodingException): Mono<ResponseStatusException> {
//        .error(ex.message, ex)
        return Mono.just(ResponseStatusException(BAD_REQUEST, ex.message))
    }
}
