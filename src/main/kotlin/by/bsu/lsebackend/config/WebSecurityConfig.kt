package by.bsu.lsebackend.config

import by.bsu.lsebackend.extension.star
import by.bsu.lsebackend.properties.JwtProperties
import by.bsu.lsebackend.security.AuthenticationManager
import by.bsu.lsebackend.security.SecurityContextRepository
import org.springframework.beans.factory.annotation.Value
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
    @Value("\${cors.origin}")
    private val corsAllowedOrigin: String
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(jwtProperties.iteration)
    }

    @Bean
    fun securityWebFilterChain(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain =
        httpSecurity
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .authenticationManager(authenticationManager)
            .securityContextRepository(securityContextRepository)
            .authorizeExchange()
            .pathMatchers(
                POST,
                "/api/v1/users/students",
                "/api/v1/users/teachers",
                "/auth/refresh-token",
                "/auth/login"
            )
            .permitAll()
            .pathMatchers(
                GET,
                "/webjars/swagger-ui/**",
                "/webjars/swagger-ui.html",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/v3/api-docs/**",
            )
            .permitAll()
            .anyExchange().authenticated()
            .and()
            .build()


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf(corsAllowedOrigin)
            allowedMethods = listOf(GET.name, POST.name, OPTIONS.name)
            allowedHeaders = listOf(String.star())
            allowCredentials = true
        }
        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
