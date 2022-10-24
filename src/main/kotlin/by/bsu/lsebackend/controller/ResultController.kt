package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.QuizResultResponse
import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.entity.QuizResult
import by.bsu.lsebackend.service.ResultService
import org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.time.Duration

@RestController
@RequestMapping("/api/v1/results")
class ResultController(private val resultService: ResultService) {

    @GetMapping("/current")
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    fun findAllPagedForCurrentUser(
        @RequestParam(value = "page", defaultValue = "0") page: Long,
        @RequestParam(value = "size", defaultValue = "10") size: Long,
        @AuthenticationPrincipal principal: Authentication
    ): Flux<QuizResult> {
        return resultService.findAllByEmail(principal.principal.toString(), page, size)
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('TEACHER')")
    fun findAllPaged(
        @RequestParam(value = "page", defaultValue = "0") page: Long,
        @RequestParam(value = "size", defaultValue = "10") size: Long,
    ): Flux<QuizResultResponse> {
        return resultService.findAll(page, size)
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('STUDENT', 'TEACHER')")
    fun submit(@RequestBody @Validated resultRequest: ResultRequest): Mono<Int> =
        resultService.check(resultRequest)

    // todo investigate repeatWhen, subscribeOn
    @GetMapping(value = ["/stream"], produces = [TEXT_EVENT_STREAM_VALUE])
    @PreAuthorize("hasRole('TEACHER')")
    fun findAllStreamed(): Flux<QuizResultResponse> = resultService.findWithTailableCursorBy()
        .repeatWhen { flux -> flux.delayElements(Duration.ofSeconds(1)) }
        .subscribeOn(Schedulers.boundedElastic())
}
