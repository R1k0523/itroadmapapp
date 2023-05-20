package ru.boringowl.itroadmap.domain.model.user


data class Token(
    val accessToken: String,
    val accessExpiration: Long,
    val refreshToken: String,
    val refreshExpiration: Long,
)