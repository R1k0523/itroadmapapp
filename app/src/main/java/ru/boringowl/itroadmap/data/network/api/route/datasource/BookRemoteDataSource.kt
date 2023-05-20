package ru.boringowl.itroadmap.data.network.api.route.datasource

import ru.boringowl.itroadmap.data.network.api.route.client.BookPostApi
import ru.boringowl.itroadmap.data.network.api.route.response.BookPostResponse
import ru.boringowl.itroadmap.data.network.api.route.response.toModel
import ru.boringowl.itroadmap.data.network.base.PageResponse
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.data.network.base.transform
import ru.boringowl.itroadmap.domain.model.competence.BookPost
import java.util.UUID
import javax.inject.Inject


class BookRemoteDataSource @Inject constructor(
    private val api: BookPostApi,
    private val handler: ResponseHandler,
) {
    suspend fun get(
        id: Int,
        page: Int = 0,
        limit: Int = 20,
        query: String = "",
    ): PageResponse<BookPost> = handler.process {
        api.get(id, page, limit, query)
    }.transform(BookPostResponse::toModel)

    suspend fun get(id: UUID): BookPost = handler.process { api.get(id) }.toModel()
}