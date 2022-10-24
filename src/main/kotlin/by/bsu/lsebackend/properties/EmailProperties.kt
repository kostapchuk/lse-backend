package by.bsu.lsebackend.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.Valid
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

// todo add messages and test messages

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "mail")
data class EmailProperties(
    @field:Valid
    val sender: Sender,
    @field:Valid
    val smtp: Smtp,
) {
    data class Sender(
        @field:NotBlank
        val password: String,
        @field:NotBlank
        val username: String,
    )

    data class Smtp(
        @field:NotBlank
        val host: String,
        @field:NotNull
        val port: Int,
        @field:NotNull
        val sslEnable: Boolean,
        @field:NotNull
        val auth: Boolean,
    )
}
