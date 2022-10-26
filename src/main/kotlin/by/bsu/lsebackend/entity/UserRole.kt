package by.bsu.lsebackend.entity

import org.springframework.security.core.authority.SimpleGrantedAuthority

private const val ROLE_PREFIX = "ROLE_"

enum class UserRole {
    ROLE_TEACHER,
    ROLE_STUDENT;

    open fun getAuthority(): SimpleGrantedAuthority {
        return SimpleGrantedAuthority(name)
    }

    fun getRoleWithoutPrefix() = name.removePrefix(ROLE_PREFIX)
}
