package ru.boringowl.itroadmap.data.usecase.route

import ru.boringowl.itroadmap.data.repository.RouteRepository
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Route
import ru.boringowl.itroadmap.domain.usecase.route.GetRoutesUseCase
import javax.inject.Inject

class GetRoutesUseCaseImpl @Inject constructor(
    private val repo: RouteRepository
) : GetRoutesUseCase {
    override suspend operator fun invoke(): FlowResult<List<Route>> =
        repo.getRoutes()
}