package ru.boringowl.itroadmap.data.network.api.auth.response

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.user.Token


@Serializable
data class TokenResponse(
    val accessToken: String,
    val accessExpiration: Long,
    val refreshToken: String,
    val refreshExpiration: Long,
)


fun TokenResponse.toToken() = Token(
    accessToken = accessToken,
    accessExpiration = accessExpiration,
    refreshToken = refreshToken,
    refreshExpiration = refreshExpiration,
)