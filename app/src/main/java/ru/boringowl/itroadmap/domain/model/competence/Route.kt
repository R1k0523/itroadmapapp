package ru.boringowl.itroadmap.domain.model.competence


class Route(
    var routeId: Int,
    var routeName: String = "",
    var routeDescription: String = "",
    var resumesCount: Int = 0,
    var vacanciesCount: Int = 0
) {
    fun index() = if (vacanciesCount != 0) resumesCount / vacanciesCount else 0
}
