package ru.boringowl.itroadmap.data.network.api.auth.request

import kotlinx.serialization.Serializable


@Serializable
data class RefreshTokenRequest(
    val refreshToken: String
)
