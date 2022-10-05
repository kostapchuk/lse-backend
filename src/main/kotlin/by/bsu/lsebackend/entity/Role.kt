package by.bsu.lsebackend.entity

import org.springframework.security.core.authority.SimpleGrantedAuthority

enum class Role {
    ROLE_TEACHER,
    ROLE_STUDENT;

    open fun getAuthority(): SimpleGrantedAuthority {
        return SimpleGrantedAuthority(name)
    }
}
