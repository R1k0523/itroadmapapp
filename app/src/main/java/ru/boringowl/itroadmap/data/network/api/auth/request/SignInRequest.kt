package ru.boringowl.itroadmap.data.network.api.auth.request

import kotlinx.serialization.Serializable


@Serializable
data class SignInRequest(
    val username: String,
    val password: String
)
