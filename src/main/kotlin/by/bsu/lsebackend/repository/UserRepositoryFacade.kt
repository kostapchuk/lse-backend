package by.bsu.lsebackend.repository

import by.bsu.lsebackend.dto.DeleteUserRequest
import by.bsu.lsebackend.dto.RefreshTokenRequest
import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.entity.Student
import by.bsu.lsebackend.entity.Teacher
import by.bsu.lsebackend.entity.UserType
import by.bsu.lsebackend.exception.BadRequestException
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserRepositoryFacade(
    private val teacherRepository: TeacherRepository,
    private val studentRepository: StudentRepository,
    private val baseUserRepository: BaseUserRepository,
    private val typeToRepositories: Map<UserType, GeneralBaseUserRepository<out BaseUser, String>>,
) {

    fun existsByEmail(email: String): Mono<Boolean> {
        return baseUserRepository.existsByEmail(email).filter { it == true }
    }

    fun delete(request: DeleteUserRequest): Mono<Void> =
        typeToRepositories[request.userType]!!.deleteById(request.userId)

    fun findByEmail(email: String, userType: UserType): Mono<out BaseUser> {
        return typeToRepositories[userType]!!.findByEmailAndUserType(email, userType)
    }

    fun <T : BaseUser> save(user: T): Mono<out BaseUser> = when (user.userType) {
        UserType.STUDENT -> {
            studentRepository.save(user as Student)
        }

        UserType.TEACHER -> {
            teacherRepository.save(user as Teacher)
        }
    }

    fun findById(userId: String): Mono<BaseUser> =
        baseUserRepository.findById(userId)
            .switchIfEmpty(Mono.error(BadRequestException("User with $userId does not exist")))

    fun findByRefreshToken(request: RefreshTokenRequest): Mono<out BaseUser> =
        typeToRepositories[request.userType]!!.findByRefreshTokenAndUserType(request.token.value, request.userType)
}
