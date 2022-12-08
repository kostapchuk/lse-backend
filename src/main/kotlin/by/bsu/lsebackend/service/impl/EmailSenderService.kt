package by.bsu.lsebackend.service.impl

import by.bsu.lsebackend.properties.EmailProperties
import by.bsu.lsebackend.service.SenderService
import org.springframework.stereotype.Service
import java.beans.BeanInfo
import java.beans.Introspector
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import javax.validation.constraints.NotNull

@Service
class EmailSenderService(
    private val emailProperties: EmailProperties,
) : SenderService {

    override fun send(
        to: String,
        message: String,
        subject: String,
    ) {
        val session = createSession()
        val mimeMsg = MimeMessage(session).apply {
            this.addRecipient(Message.RecipientType.TO, InternetAddress(to))
            this.subject = subject
            this.setText(message)
            this.setFrom(InternetAddress(emailProperties.sender.username))
        }
        Transport.send(mimeMsg)
    }

    private fun createSession(): Session = Session.getInstance(
        toProperties(emailProperties.smtp),
        object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(emailProperties.sender.username, emailProperties.sender.password)
            }
        }
    )

    private companion object fun <T> toProperties(@NotNull t: T) = Properties().apply {
        val c = (t ?: return@apply)::class.java as Class<*>
        val beanInfo: BeanInfo = Introspector.getBeanInfo(c)
        for (pd in beanInfo.propertyDescriptors) {
            val name = pd.name
            val o = pd.readMethod.invoke(t)
            if (o != null) {
                this.setProperty(name, o.toString())
            }
        }
    }
}
