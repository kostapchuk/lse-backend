package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.service.ResultService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/results")
class ResultController(private val resultService: ResultService) {

    @PostMapping
    fun check(@Valid @RequestBody resultRequest: ResultRequest): Mono<Int> =
        resultService.check(resultRequest)
}
