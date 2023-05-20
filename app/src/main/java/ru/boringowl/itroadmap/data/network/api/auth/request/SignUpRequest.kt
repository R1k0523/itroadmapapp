package ru.boringowl.itroadmap.data.network.api.auth.request

import kotlinx.serialization.Serializable


@Serializable
data class SignUpRequest(
    val username: String,
    val email: String,
    val password: String,
    val matchingPassword: String,
)
