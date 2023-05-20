package ru.boringowl.itroadmap.data.network.api.auth.request

import kotlinx.serialization.Serializable


@Serializable
data class NewPasswordRequest(
    val password: String,
    val matchingPassword: String
)
