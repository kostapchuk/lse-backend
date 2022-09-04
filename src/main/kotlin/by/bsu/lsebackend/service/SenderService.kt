package by.bsu.lsebackend.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
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
class SenderService(private val mailRuSmtpProperties: Properties) {

    // todo move to config

    @Value("\${email.sender.username}")
    lateinit var senderUsername: String

    @Value("\${email.sender.password}")
    lateinit var senderPassword: String

    @Async
    fun send(
        to: String,
        message: String,
        subject: String
    ) {
        val session = Session.getInstance(mailRuSmtpProperties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(senderUsername, senderPassword)
            }
        })
        try {
            val mimeMsg = MimeMessage(session)
            mimeMsg.setFrom(InternetAddress(senderUsername))
            mimeMsg.addRecipient(Message.RecipientType.TO, InternetAddress(to))
            mimeMsg.subject = subject
            mimeMsg.setText(message)
            Transport.send(mimeMsg)
        } catch (mex: MessagingException) {
            println("Cannot send email: $mex")
        }
    }
}
