package ru.boringowl.itroadmap.domain.model.competence

import java.util.UUID

data class Skill(
    var skillId: UUID? = null,
    var skillName: String = "",
    var necessity: Int = 0,
    var route: Route? = null,
)
