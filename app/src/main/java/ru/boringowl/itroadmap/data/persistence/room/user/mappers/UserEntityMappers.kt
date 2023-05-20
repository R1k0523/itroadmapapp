package ru.boringowl.itroadmap.data.persistence.room.user.mappers

import ru.boringowl.itroadmap.data.persistence.room.user.entity.UserEntity
import ru.boringowl.itroadmap.domain.model.user.User
import ru.boringowl.itroadmap.domain.model.user.UserRole
import java.util.UUID

fun UserEntity.toModel() = User(
    id = UUID.fromString(id),
    username = username,
    email = email,
    enabled = enabled,
    role = UserRole.valueOf(role),
    imageId = imageId,
    description = description,
    fullName = fullName,
    rating = rating,
)


fun User.toEntity() = UserEntity(
    id = id.toString(),
    username = username,
    email = email,
    role = role.name,
    imageId = imageId,
    description = description,
    fullName = fullName,
    rating = rating,
    enabled = enabled,
)