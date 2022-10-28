package by.bsu.lsebackend.security

import by.bsu.lsebackend.security.SecurityConstant.BEARER
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.Optional

@Component
class SecurityContextRepository(private val authenticationManager: AuthenticationManager) :
    ServerSecurityContextRepository {

    override fun save(exchange: ServerWebExchange, context: SecurityContext): Mono<Void> {
        throw IllegalStateException("Save method not supported!")
    }

    override fun load(exchange: ServerWebExchange): Mono<SecurityContext> =
        Optional.ofNullable(exchange.request.headers.getFirst(AUTHORIZATION))
            .filter { it.startsWith(BEARER) }
            .map { authHeader ->
                val authToken = authHeader.substring(BEARER.length)
                val auth = UsernamePasswordAuthenticationToken(authToken, authToken)
                return@map authenticationManager
                    .authenticate(auth)
                    .map { SecurityContextImpl(it) as SecurityContext }
            }
            .orElse(Mono.empty())

}
