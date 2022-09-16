package by.bsu.lsebackend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "email.sender")
class EmailSenderConfig {
    lateinit var password: String
    lateinit var username: String
}
