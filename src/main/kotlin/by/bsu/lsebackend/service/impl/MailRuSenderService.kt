package by.bsu.lsebackend.service.impl

import by.bsu.lsebackend.config.EmailSenderConfig
import by.bsu.lsebackend.service.SenderService
import org.springframework.stereotype.Service
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class MailRuSenderService(
    private val mailRuSmtpProperties: Properties,
    private val emailSenderConfig: EmailSenderConfig,
) : SenderService {

//    @Async
    override fun send(
        to: String,
        message: String,
        subject: String,
    ) {
        val session = Session.getInstance(mailRuSmtpProperties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(emailSenderConfig.username, emailSenderConfig.password)
            }
        })
        try {
            val mimeMsg = MimeMessage(session)
            mimeMsg.setFrom(InternetAddress(emailSenderConfig.username))
            mimeMsg.addRecipient(Message.RecipientType.TO, InternetAddress(to))
            mimeMsg.subject = subject
            mimeMsg.setText(message)
            Transport.send(mimeMsg)
        } catch (mex: MessagingException) {
            println("Cannot send email: $mex")
        }
    }
}

