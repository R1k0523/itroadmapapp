package ru.boringowl.itroadmap.data.network.api.route.response

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.competence.Skill
import java.util.UUID

@Serializable
data class SkillResponse(
    var skillId: String,
    var skillName: String = "",
    var necessity: Int = 0,
    var route: RouteResponse? = null,
)


fun SkillResponse.toModel() = Skill(
    UUID.fromString(skillId),
    skillName,
    necessity,
    route?.toModel(),
)