package ru.boringowl.itroadmap.data.network.api.file.response

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.file.FileMeta

@Serializable
data class FileMetaResponse(
    val id: String,
    val name: String,
    val size: Long,
    val uploadAt: Long
)


fun FileMetaResponse.toModel() = FileMeta(
    id = id,
    name = name,
    size = size,
    uploadAt = uploadAt,
)