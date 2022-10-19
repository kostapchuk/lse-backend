package by.bsu.lsebackend.config

import by.bsu.lsebackend.entity.BaseUser
import by.bsu.lsebackend.entity.UserType
import by.bsu.lsebackend.repository.GeneralBaseUserRepository
import by.bsu.lsebackend.repository.StudentRepository
import by.bsu.lsebackend.repository.TeacherRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationConfig {

    @Bean
    fun buildTypeToRepositories(
        studentRepository: StudentRepository,
        teacherRepository: TeacherRepository,
    ): Map<UserType, GeneralBaseUserRepository<out BaseUser, String>> =
        mapOf(UserType.STUDENT to studentRepository, UserType.TEACHER to teacherRepository)
}
