package ru.boringowl.itroadmap.data.network.api.route.datasource

import ru.boringowl.itroadmap.data.network.api.route.client.RouteApi
import ru.boringowl.itroadmap.data.network.api.route.response.RouteResponse
import ru.boringowl.itroadmap.data.network.api.route.response.toModel
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.domain.model.competence.Route
import javax.inject.Inject


class RouteRemoteDataSource @Inject constructor(
    private val api: RouteApi,
    private val handler: ResponseHandler,
) {
    suspend fun get(): List<Route> = handler.process { api.get() }.items.map(RouteResponse::toModel)
    suspend fun getRoute(id: Int): Route = handler.process { api.get(id) }.toModel()
}