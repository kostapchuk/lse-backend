package by.bsu.lsebackend.dto

import com.fasterxml.jackson.annotation.JsonProperty

class RegisterResponse(
    @JsonProperty("firstName")
    val firstName: String,

    @JsonProperty("lastName")
    val lastName: String,

    @JsonProperty("email")
    val email: String,
)
