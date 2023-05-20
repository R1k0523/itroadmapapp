package ru.boringowl.itroadmap.data.network.api.route.response

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.competence.BookInfo
import ru.boringowl.itroadmap.domain.model.competence.BookPost
import java.util.UUID

@Serializable
data class BookPostResponse(
    var bookPostId: String,
    var routeId: Int? = null,
    var description: String = "",
    var books: List<BookInfoResponse> = listOf()
)

@Serializable
data class BookInfoResponse (
    var url: String = "",
    var filename: String = "",
    var size: Int = 0
)


fun BookPostResponse.toModel() = BookPost(
    UUID.fromString(bookPostId),
    routeId,
    description,
    books.map(BookInfoResponse::toModel),
)

fun BookInfoResponse.toModel() = BookInfo(
    url,
    filename,
    size,
)