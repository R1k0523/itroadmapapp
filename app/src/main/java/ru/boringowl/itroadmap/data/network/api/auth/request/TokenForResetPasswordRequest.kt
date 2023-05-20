package ru.boringowl.itroadmap.data.network.api.auth.request

import kotlinx.serialization.Serializable


@Serializable
data class TokenForResetPasswordRequest(
    val email: String,
    val code: String
)
