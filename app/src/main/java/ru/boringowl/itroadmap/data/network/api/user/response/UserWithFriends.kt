package ru.boringowl.itroadmap.data.network.api.user.response

import kotlinx.serialization.Serializable


@Serializable
data class UserWithFriends(
    val user: UserResponse,
    val friends: List<UserShortResponse> = listOf(),
)

