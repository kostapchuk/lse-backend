package by.bsu.lsebackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.Properties

@Configuration
class MailConfig {

    // todo move to properties

    @Bean
    @Primary
    fun mailRuConfig(): Properties {
        return System.getProperties().apply {
            this["mail.smtp.host"] = "smtp.mail.ru"
            this["mail.smtp.port"] = "465"
            this["mail.smtp.ssl.enable"] = true
            this["mail.smtp.auth"] = true
        }
    }
}
