package ru.boringowl.itroadmap.data.network.api.route.response

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.competence.Route


@Serializable
class RouteResponse(
    var routeId: Int,
    var routeName: String,
    var routeDescription: String,
    var resumesCount: Int,
    var vacanciesCount: Int,
)


fun RouteResponse.toModel() = Route(
    routeId,
    routeName,
    routeDescription,
    resumesCount,
    vacanciesCount,
)