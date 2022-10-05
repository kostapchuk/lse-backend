package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.entity.QuizResult
import by.bsu.lsebackend.service.ResultService
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

@RestController
@RequestMapping("/api/v1/results")
class ResultController(private val resultService: ResultService) {

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    fun check(@RequestBody @Validated resultRequest: ResultRequest): Mono<Int> =
        resultService.check(resultRequest)

    // todo investigate repeatWhen, subscribeOn
    @GetMapping(value = ["/stream"], produces = [TEXT_EVENT_STREAM_VALUE])
    @PreAuthorize("hasRole('TEACHER')")
    fun findAllStreamed(): Flux<QuizResult> = resultService.findWithTailableCursorBy()
        .repeatWhen { flux -> flux.delayElements(Duration.ofSeconds(1)) }
        .subscribeOn(Schedulers.boundedElastic())
}
