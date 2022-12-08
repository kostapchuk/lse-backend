package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@JsonIgnoreProperties(ignoreUnknown = true)
class Token(
    @field:NotNull
    val type: TokenType,
    @field:NotBlank
    val value: String,
)
