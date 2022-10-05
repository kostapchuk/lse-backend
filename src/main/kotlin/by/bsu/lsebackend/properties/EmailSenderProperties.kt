package by.bsu.lsebackend.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "email.sender")
data class EmailSenderProperties(
    @field:NotBlank
    val password: String,
    @field:NotBlank
    val username: String,
)
