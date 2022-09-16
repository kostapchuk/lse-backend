package by.bsu.lsebackend.exception

import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(DecodingException::class)
    @ResponseStatus(BAD_REQUEST)
    fun decodingExceptionHandler(ex: DecodingException): Mono<ResponseStatusException> {
        return Mono.error(ResponseStatusException(BAD_REQUEST, ex.message))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(BAD_REQUEST)
    fun methodArgumentNotValidExceptionHandler(ex: MethodArgumentNotValidException): Mono<ResponseStatusException> {
        return Mono.error(ResponseStatusException(BAD_REQUEST, ex.message))
    }

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun webExchangeBindExceptionHandler(ex: WebExchangeBindException): Mono<ResponseStatusException> {
        return Mono.error(
            ResponseStatusException(
                BAD_REQUEST,
                ex.bindingResult.fieldErrors.map { it.field to it.defaultMessage }.joinToString()
            )
        )
    }

    // todo: is it needed?

//    @ExceptionHandler(Exception::class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    fun serverExceptionHandler(ex: Exception): String? {
//        return ex.message
//    }
}
