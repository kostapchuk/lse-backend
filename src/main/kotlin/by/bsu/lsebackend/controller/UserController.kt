package by.bsu.lsebackend.controller

import by.bsu.lsebackend.dto.UserRequest
import by.bsu.lsebackend.dto.UserResponse
import by.bsu.lsebackend.service.UserService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) = userService.findById(id)

    @GetMapping
    fun findAll(): Flux<UserResponse> = userService.findAll()

    @PostMapping
    @ResponseStatus(CREATED)
    fun save(@RequestBody @Valid userRequest: UserRequest) = userService.save(userRequest)

    @PutMapping("/{id}")
    fun updateById(@PathVariable id: String, @RequestBody @Valid userRequest: UserRequest) =
        userService.updateById(id, userRequest)

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    fun deleteById(@PathVariable id: String) = userService.deleteById(id)

}
