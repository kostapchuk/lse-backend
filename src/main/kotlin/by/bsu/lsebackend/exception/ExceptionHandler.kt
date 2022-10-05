package by.bsu.lsebackend.exception

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.core.codec.DecodingException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

// todo: add logger
@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class, DecodingException::class)
    @ResponseStatus(BAD_REQUEST)
    fun methodArgumentNotValidOrDecodingExceptionHandler(ex: RuntimeException): Mono<ResponseStatusException> {
        return Mono.error(ResponseStatusException(BAD_REQUEST, ex.message))
    }

    @ExceptionHandler(WebExchangeBindException::class)
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun webExchangeBindExceptionHandler(ex: WebExchangeBindException): Mono<ResponseStatusException> {
        return Mono.error(
            ResponseStatusException(
                UNPROCESSABLE_ENTITY,
                ex.bindingResult.fieldErrors.map { it.field to it.defaultMessage }.joinToString()
            )
        )
    }

    @ExceptionHandler(JwtException::class)
    @ResponseStatus(UNAUTHORIZED)
    fun jwtException(ex: JwtException): Mono<ResponseStatusException> {
        return Mono.error(
            ResponseStatusException(
                UNAUTHORIZED,
                ex.message
            )
        )
    }

    @ExceptionHandler(
        ExpiredJwtException::class,
        UnsupportedJwtException::class,
        MalformedJwtException::class,
        SignatureException::class
    )
    @ResponseStatus(UNAUTHORIZED)
    fun jwtExceptionsHandling(ex: RuntimeException): Mono<ResponseStatusException> {
        return Mono.error(
            ResponseStatusException(
                UNAUTHORIZED,
                ex.message
            )
        )
    }

//    @ExceptionHandler(AuthenticationException::class)
//    @ResponseStatus(UNAUTHORIZED)
//    fun authenticationException(ex: AuthenticationException): Mono<ResponseStatusException> {
//        return Mono.error(
//            ResponseStatusException(
//                UNAUTHORIZED,
//                "some custom msg"
//            )
//        )
//    }
//
//    @ExceptionHandler(AccessDeniedException::class)
//    @ResponseStatus(FORBIDDEN)
//    fun accessDeniedException(ex: AccessDeniedException): Mono<ResponseStatusException> {
//        return Mono.error(
//            ResponseStatusException(
//                FORBIDDEN,
//                "some custom msg FORBIDDEN"
//            )
//        )
//    }

    // todo: is it needed?

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun serverExceptionHandler(ex: Throwable): String? {
        return ex.message
    }
}
