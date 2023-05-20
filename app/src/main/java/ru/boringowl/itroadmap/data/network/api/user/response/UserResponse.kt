package ru.boringowl.itroadmap.data.network.api.user.response

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.user.User
import ru.boringowl.itroadmap.domain.model.user.UserRole
import java.util.UUID

@Serializable
data class UserResponse(
    val id: String,
    val username: String,
    val email: String,
    val enabled: Boolean,
    val role: String,
    val imageId: String? = null,
    val description: String,
    val fullName: String,
    val rating: Long,
)

@Serializable
data class UserShortResponse(
    val id: String,
    val username: String,
    val email: String,
)



fun UserResponse.toUser() = User(
    UUID.fromString(id),
    username,
    email,
    enabled,
    UserRole.valueOf(role),
    imageId,
    description,
    fullName,
    rating,
)