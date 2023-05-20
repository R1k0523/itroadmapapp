package ru.boringowl.itroadmap.domain.model.user

import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val email: String,
    val enabled: Boolean,
    val role: UserRole,
    val imageId: String? = null,
    val description: String,
    val fullName: String,
    val rating: Long,
)


enum class UserRole {
    DEFAULT, ADMIN
}

data class UserShort(
    val id: UUID,
    val username: String,
    val email: String,
)
