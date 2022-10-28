package by.bsu.lsebackend.entity

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class UserRole {
    ROLE_TEACHER,
    ROLE_STUDENT;

    open fun getAuthority(): SimpleGrantedAuthority {
        return SimpleGrantedAuthority(name)
    }
}
