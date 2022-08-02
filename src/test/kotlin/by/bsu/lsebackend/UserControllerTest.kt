package by.bsu.lsebackend

import by.bsu.lsebackend.dto.UserRequest
import by.bsu.lsebackend.dto.UserResponse
import by.bsu.lsebackend.entity.User
import by.bsu.lsebackend.mapper.toResponse
import by.bsu.lsebackend.service.UserService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

//TODO:
// - remove mockito add test containers and rewrite to integration tests
// - test find by id should not find when id is incorrect
// - test find all should return empty list
// - test save should not save when data is incorrect
// - test update by id should not update when id not found
// - test update by id should not update when request data is incorrect
// - test delete by id should not delete when id not found

class UserControllerTest(@Autowired private val client: WebTestClient) : BaseControllerTest() {

    @MockBean
    private lateinit var userService: UserService

    @Test
    fun `add user should add`() {
        // given
        val userRequest = UserRequest("Kirill", 45)
        val user = User("qwe123", "Kirill", 45)
        // when
        Mockito.`when`(userService.save(userRequest)).then { Mono.just(user) }
        // verify
        client.post().uri("/api/v1/users")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(userRequest).exchange()
            .expectStatus().isCreated
            .expectBody<UserResponse>().isEqualTo(user.toResponse())
    }

    @Test
    fun `find user by existing id should find`() {
        // given
        val user = User("qwe123", "Kirill", 45)
        // when
        Mockito.`when`(userService.findById("qwe123")).then { Mono.just(user) }
        // verify
        client.get().uri("/api/v1/users/qwe123").exchange()
            .expectStatus().isOk
            .expectBody<UserResponse>().isEqualTo(user.toResponse())
    }

    @Test
    fun `find all should find`() {
        // given
        val user1 = User("qwe123", "Kirill", 45)
        val user2 = User("iop789", "Vasya", 22)
        // when
        Mockito.`when`(userService.findAll()).then { Flux.just(user1, user2).map { it.toResponse() } }
        // verify
        client.get().uri("/api/v1/users").exchange()
            .expectStatus().isOk
            .expectBodyList<UserResponse>()
            .hasSize(2)
            .contains(user1.toResponse(), user2.toResponse())
    }

    @Test
    fun `update user by id should update`() {
        // given
        val userRequest = UserRequest("Stepan", 65)
        val userNew = User("qwe123", "Stepan", 65)
        // when
        Mockito.`when`(userService.updateById("qwe123", userRequest)).then { Mono.just(userNew.toResponse()) }
        // verify
        client.put().uri("/api/v1/users/qwe123")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(userRequest).exchange()
            .expectStatus().isOk
            .expectBody<UserResponse>().isEqualTo(userNew.toResponse())
    }

    @Test
    fun `delete user by id should delete`() {
        // when
        Mockito.`when`(userService.deleteById("qwe123")).then { Mono.empty<Void>() }
        // verify
        client.delete().uri("/api/v1/users/qwe123").exchange()
            .expectStatus().isNoContent
    }

}
