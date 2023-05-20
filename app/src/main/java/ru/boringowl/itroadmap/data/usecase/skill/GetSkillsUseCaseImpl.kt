package ru.boringowl.itroadmap.data.usecase.skill

import ru.boringowl.itroadmap.data.repository.SkillRepository
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Skill
import ru.boringowl.itroadmap.domain.usecase.skill.GetSkillsUseCase
import javax.inject.Inject

class GetSkillsUseCaseImpl @Inject constructor(
    private val repo: SkillRepository
): GetSkillsUseCase {
    override suspend operator fun invoke(routeId: Int): FlowResult<List<Skill>> =
        repo.getSkills(routeId)
}