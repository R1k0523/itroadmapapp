package ru.boringowl.itroadmap.domain.usecase.skill

import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Skill

interface GetSkillsUseCase {
    suspend operator fun invoke(routeId: Int): FlowResult<List<Skill>>
}