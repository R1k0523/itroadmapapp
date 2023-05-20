package ru.boringowl.itroadmap.data.network.api.user.request

import kotlinx.serialization.Serializable


@Serializable
data class UpdateRequest(
    val username: String? = null,
    val email: String? = null,
    val description: String,
    val fullName: String,
)
