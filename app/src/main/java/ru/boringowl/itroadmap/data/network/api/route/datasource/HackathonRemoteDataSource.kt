package ru.boringowl.itroadmap.data.network.api.route.datasource

import ru.boringowl.itroadmap.data.network.api.route.client.HackApi
import ru.boringowl.itroadmap.data.network.api.route.response.HackathonResponse
import ru.boringowl.itroadmap.data.network.api.route.response.toModel
import ru.boringowl.itroadmap.data.network.base.PageResponse
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.data.network.base.transform
import ru.boringowl.itroadmap.domain.model.competence.Hackathon
import java.util.UUID
import javax.inject.Inject


class HackathonRemoteDataSource @Inject constructor(
    private val api: HackApi,
    private val handler: ResponseHandler,
) {
    suspend fun get(
        page: Int = 0,
        limit: Int = 20,
        query: String = "",
    ): PageResponse<Hackathon> = handler.process { api.get(page, limit, query) }.transform(HackathonResponse::toModel)

    suspend fun get(id: UUID): Hackathon = handler.process { api.get(id) }.toModel()
}