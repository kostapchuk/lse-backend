package by.bsu.lsebackend.service

import org.springframework.scheduling.annotation.Async

interface SenderService {

    @Async
    fun send(to: String, message: String, subject: String,)
}
