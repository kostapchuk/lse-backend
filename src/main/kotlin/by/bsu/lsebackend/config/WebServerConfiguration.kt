package by.bsu.lsebackend.config

import by.bsu.lsebackend.extension.empty
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.HttpHeaders.CONTENT_TYPE
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.OPTIONS
import org.springframework.http.HttpMethod.POST
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebServerConfiguration : WebFluxConfigurer {

    @Value("\${cors.origins}")
    private val corsOrigins: String = String.empty()

    override fun addCorsMappings(registry: CorsRegistry) {
        if (corsOrigins.isEmpty()) {
            // todo replace with custom exception
            throw IllegalArgumentException("Cors origin url is not provided.")
        }
        val allowedOrigins = corsOrigins.split(",").toTypedArray()
        registry.addMapping("/api/v1/**")
            .allowedOrigins(*allowedOrigins)
            .allowedMethods(POST.name, GET.name, OPTIONS.name)
            .allowedHeaders(CONTENT_TYPE, AUTHORIZATION)
    }
}
