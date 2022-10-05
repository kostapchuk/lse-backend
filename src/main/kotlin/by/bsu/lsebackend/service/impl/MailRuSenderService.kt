package by.bsu.lsebackend.service.impl

import by.bsu.lsebackend.properties.EmailSenderProperties
import by.bsu.lsebackend.service.SenderService
import org.springframework.stereotype.Service
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class MailRuSenderService(
    private val mailRuSmtpProperties: Properties,
    private val emailSenderProperties: EmailSenderProperties,
) : SenderService {

    override fun send(
        to: String,
        message: String,
        subject: String,
    ) {
        val session = Session.getInstance(
            mailRuSmtpProperties,
            object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(emailSenderProperties.username, emailSenderProperties.password)
                }
            }
        )
        val mimeMsg = MimeMessage(session).apply {
            this.addRecipient(Message.RecipientType.TO, InternetAddress(to))
            this.subject = subject
            this.setText(message)
            this.setFrom(InternetAddress(emailSenderProperties.username))
        }
        Transport.send(mimeMsg)
    }
}
