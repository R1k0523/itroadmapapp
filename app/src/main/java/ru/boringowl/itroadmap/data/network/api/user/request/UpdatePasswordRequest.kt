package ru.boringowl.itroadmap.data.network.api.user.request

import kotlinx.serialization.Serializable


@Serializable
data class UpdatePasswordRequest(
    val password: String,
    val matchingPassword: String,
    val oldPassword: String,
)
