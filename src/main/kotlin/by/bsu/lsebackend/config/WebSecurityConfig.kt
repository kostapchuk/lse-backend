package by.bsu.lsebackend.config

import by.bsu.lsebackend.properties.JwtProperties
import by.bsu.lsebackend.security.AuthenticationManager
import by.bsu.lsebackend.security.SecurityContextRepository
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource

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
        return httpSecurity
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(
                "/api/v1/users/register-student",
                "/api/v1/users/register-teacher",
                "/auth/refresh-token",
                "/auth/login",
                "/api/v1/quizzes/topics",
                "/webjars/swagger-ui/**", "/webjars/swagger-ui.html",
                "/swagger-ui/**", "/swagger-ui.html",
                "/v3/api-docs/**",
            ).permitAll()
            .anyExchange().authenticated()
            .and()
            .build()
        // todo mvc matchers and exact method name (get, post)
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:3000") // todo move to config
//            allowedOrigins = listOf("*") // todo move to config
            allowedMethods = listOf(GET.name, POST.name, OPTIONS.name)
            allowedHeaders = listOf("*")
            allowCredentials = true // todo investigate what headers are required
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
