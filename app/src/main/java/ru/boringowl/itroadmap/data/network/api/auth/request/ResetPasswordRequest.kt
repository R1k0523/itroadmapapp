package ru.boringowl.itroadmap.data.network.api.auth.request

import kotlinx.serialization.Serializable


@Serializable
data class ResetPasswordRequest(
    val email: String
)
