package ru.boringowl.itroadmap.data.network.api.route.datasource

import ru.boringowl.itroadmap.data.network.api.route.client.CompanyApi
import ru.boringowl.itroadmap.data.network.api.route.response.CompanyResponse
import ru.boringowl.itroadmap.data.network.api.route.response.toModel
import ru.boringowl.itroadmap.data.network.base.PageResponse
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.data.network.base.transform
import ru.boringowl.itroadmap.domain.model.company.Company
import java.util.UUID
import javax.inject.Inject


class CompanyRemoteDataSource @Inject constructor(
    private val api: CompanyApi,
    private val handler: ResponseHandler,
) {
    suspend fun get(
        page: Int = 0,
        limit: Int = 20,
        query: String = "",
    ): PageResponse<Company> = handler.process { api.get(page, limit, query) }.transform(CompanyResponse::toModel)

    suspend fun get(id: UUID): Company = handler.process { api.get(id) }.toModel()
}