package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.QuizResultResponse
import by.bsu.lsebackend.dto.ResultRequest
import by.bsu.lsebackend.service.ResultService
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

@RestController
@RequestMapping("/api/v1/results")
class ResultController(private val resultService: ResultService) {

    @GetMapping("/current")
    @PreAuthorize(
        "hasAnyAuthority(T(by.bsu.lsebackend.entity.UserRole).ROLE_TEACHER," +
            "T(by.bsu.lsebackend.entity.UserRole).ROLE_STUDENT)"
    )
    fun findAllPagedForCurrentUser(
        @RequestParam(value = "page", defaultValue = "0") page: Long,
        @RequestParam(value = "size", defaultValue = "10") size: Long,
        @AuthenticationPrincipal principal: Authentication,
    ): Flux<QuizResultResponse> =
        resultService.findAllByEmail(principal.principal.toString(), page, size)

    @GetMapping
    @PreAuthorize("hasAuthority(T(by.bsu.lsebackend.entity.UserRole).ROLE_TEACHER)")
    fun findAllPaged(
        @RequestParam(value = "page", defaultValue = "0") page: Long,
        @RequestParam(value = "size", defaultValue = "10") size: Long,
    ): Flux<QuizResultResponse> =
        resultService.findAll(page, size)

    @PostMapping
    @PreAuthorize(
        "hasAnyAuthority(T(by.bsu.lsebackend.entity.UserRole).ROLE_TEACHER," +
            "T(by.bsu.lsebackend.entity.UserRole).ROLE_STUDENT)"
    )
    fun submit(@RequestBody @Validated resultRequest: ResultRequest): Mono<Int> =
        resultService.check(resultRequest)
}
