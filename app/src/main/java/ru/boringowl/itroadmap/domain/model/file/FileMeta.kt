package ru.boringowl.itroadmap.domain.model.file

data class FileMeta(
    val id: String,
    val name: String,
    val size: Long,
    val uploadAt: Long
)
