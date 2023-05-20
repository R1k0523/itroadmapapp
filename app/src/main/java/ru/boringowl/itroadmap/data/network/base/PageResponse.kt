package ru.boringowl.itroadmap.data.network.base

import kotlinx.serialization.Serializable

@Serializable
data class PageResponse<T>(
    val content: List<T>,
    val totalPages: Int,
    val number: Int,
    val last: Boolean,
    val first: Boolean,
    val empty: Boolean,
)


fun <T, N> PageResponse<T>.transform(block: T.() -> N) = PageResponse(
    content.map(block),
    totalPages,
    number,
    last,
    first,
    empty,
)