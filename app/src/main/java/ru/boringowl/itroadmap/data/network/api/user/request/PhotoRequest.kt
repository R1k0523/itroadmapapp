package ru.boringowl.itroadmap.data.network.api.user.request

import kotlinx.serialization.Serializable

@Serializable
data class PhotoRequest(
    val imageId: String,
)