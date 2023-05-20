package ru.boringowl.itroadmap.data.network.api.user.request

import kotlinx.serialization.Serializable

@Serializable
data class FriendRequest(
    var userId: String,
    var accept: Boolean = false,
)

