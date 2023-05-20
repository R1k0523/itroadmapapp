package ru.boringowl.itroadmap.data.network.base

import kotlinx.serialization.Serializable

@Serializable
data class StringResponse(
    val message: String? = ""
)