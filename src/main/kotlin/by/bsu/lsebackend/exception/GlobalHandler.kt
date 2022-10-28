package by.bsu.lsebackend.exception

import by.bsu.lsebackend.dto.ErrorResponse
import by.bsu.lsebackend.extension.empty
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.JwtException
import mu.KotlinLogging
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono

private val logger = KotlinLogging.logger {}

@Component
@Order(-2)
class GlobalHandler : ErrorWebExceptionHandler {
    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        val httpResponse: ServerHttpResponse = exchange.response
        val errorResponse = retrieveErrorDto(ex)
        httpResponse.statusCode = errorResponse.status
        httpResponse.headers[HttpHeaders.CONTENT_TYPE] = MediaType.APPLICATION_JSON_VALUE
        return httpResponse.writeWith(
            Mono.fromSupplier {
                val bufferFactory: DataBufferFactory = httpResponse.bufferFactory()
                try {
                    return@fromSupplier bufferFactory.wrap(
                        ObjectMapper().writeValueAsBytes(
                            mapOf(
                                "status" to errorResponse.status.value(),
                                "message" to errorResponse.message,
                                "datetime" to errorResponse.datetime
                            )
                        )
                    )
                } catch (e: JsonProcessingException) {
                    return@fromSupplier bufferFactory.wrap(ByteArray(0))
                }
            }
        )
    }

    private fun retrieveErrorDto(ex: Throwable): ErrorResponse {
        logger.error { ex }
        return when (ex) {
            is JwtException -> {
                ErrorResponse(HttpStatus.UNAUTHORIZED, ex.message ?: String.empty())
            }

            is MethodArgumentNotValidException -> {
                ErrorResponse(HttpStatus.BAD_REQUEST, ex.message)
            }

            is WebExchangeBindException -> {
                ErrorResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY,
                    ex.bindingResult.fieldErrors.map { it.field to it.defaultMessage }.joinToString(),
                )
            }

            is ServerWebInputException -> {
                ErrorResponse(HttpStatus.BAD_REQUEST, ex.reason ?: String.empty())
            }

            is BadRequestException -> {
                ErrorResponse(HttpStatus.BAD_REQUEST, ex.message)
            }

            is ResponseStatusException -> {
                ErrorResponse(ex.status, ex.message)
            }

            else -> {
                ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: String.empty())
            }
        }
    }
}
