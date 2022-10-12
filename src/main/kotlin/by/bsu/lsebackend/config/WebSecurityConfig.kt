package by.bsu.lsebackend.config

import by.bsu.lsebackend.properties.JwtProperties
import by.bsu.lsebackend.security.AuthenticationManager
import by.bsu.lsebackend.security.SecurityContextRepository
import org.springframework.context.annotation.Bean
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.nio.charset.StandardCharsets


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class WebSecurityConfig(
    private val authenticationManager: AuthenticationManager,
    private val securityContextRepository: SecurityContextRepository,
    private val jwtProperties: JwtProperties,
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(jwtProperties.iteration)
    }

    @Bean
    fun securityWebFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        // todo customize messages
        return httpSecurity
            .exceptionHandling()
            .authenticationEntryPoint { swe: ServerWebExchange, e: AuthenticationException ->
                swe.response.statusCode = HttpStatus.UNAUTHORIZED
                val bytes: ByteArray = e.message?.toByteArray(StandardCharsets.UTF_8) ?: ByteArray(0)
                val buffer: DataBuffer = swe.response.bufferFactory().wrap(bytes)
                swe.response.writeWith(Flux.just(buffer))
            }
            .accessDeniedHandler { swe: ServerWebExchange, _: AccessDeniedException ->
                Mono.fromRunnable {
                    swe.response.statusCode = HttpStatus.FORBIDDEN
                }
            }
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(
                "/register-teacher",
                "/register-student",
                "/refresh-token",
                "/login",
            ).permitAll()
            .anyExchange().authenticated()
            .and()
            .build()
        // todo mvc matchers and exact method name (get, post)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("*")
            allowedHeaders = listOf("*")
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
