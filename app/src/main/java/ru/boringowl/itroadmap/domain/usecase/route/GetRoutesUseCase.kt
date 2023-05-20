package ru.boringowl.itroadmap.domain.usecase.route

import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Route

interface GetRoutesUseCase {
    suspend operator fun invoke(): FlowResult<List<Route>>
}