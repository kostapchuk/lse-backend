package by.bsu.lsebackend.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.convert.DurationUnit
import org.springframework.validation.annotation.Validated
import java.time.Duration
import java.time.temporal.ChronoUnit.MINUTES
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

// todo add messages and test messages

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    @field:NotBlank
    @field:Min(32)
    val secret: String,
    @field:Positive
    val iteration: Int,
    @field:NotNull
    @field:DurationUnit(MINUTES)
    val accessTokenExpiration: Duration,
    @field:NotNull
    @field:DurationUnit(MINUTES)
    val refreshTokenExpiration: Duration,
)
