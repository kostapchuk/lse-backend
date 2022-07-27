package by.bsu.lsebackend.dto

import javax.validation.constraints.NotBlank

data class UserRequest(
    @NotBlank val firstName: String,
    @NotBlank val age: Int
)