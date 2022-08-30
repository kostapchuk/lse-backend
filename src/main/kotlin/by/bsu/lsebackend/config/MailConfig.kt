package by.bsu.lsebackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.Properties

@Configuration
class MailConfig {

    @Bean
    @Primary
    fun mailRuConfig(): Properties {
        val properties = System.getProperties()
        properties["mail.smtp.host"] = "smtp.mail.ru"
        properties["mail.smtp.port"] = "465"
        properties["mail.smtp.ssl.enable"] = true
        properties["mail.smtp.auth"] = true
        return properties
    }
}
