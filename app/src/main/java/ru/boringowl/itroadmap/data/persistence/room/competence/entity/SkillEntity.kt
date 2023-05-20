package ru.boringowl.itroadmap.data.persistence.room.competence.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.boringowl.itroadmap.domain.model.competence.Route
import ru.boringowl.itroadmap.domain.model.competence.Skill
import java.util.UUID

@Entity(tableName = "skills")
class SkillEntity (
    @PrimaryKey
    @ColumnInfo(name="skill_id")
    var skillId: UUID,
    @ColumnInfo(name="skill_name")
    var skillName: String = "",
    @ColumnInfo(name="necessity")
    var necessity: Int = 0,
    @ColumnInfo(name="route")
    var route_id: Int? = null
)

fun SkillEntity.toModel(route: Route? = null): Skill = Skill(
    skillId = skillId,
    skillName = skillName,
    necessity = necessity,
    route = route,
)
fun Skill.toEntity(routeId: Int? = null): SkillEntity = SkillEntity(
    skillId = skillId!!,
    skillName = skillName,
    necessity = necessity,
    route_id = routeId,
)
data class SkillWithRoute(
    @Embedded val skill: SkillEntity,
    @Relation(
        parentColumn = "route",
        entityColumn = "route_id"
    )
    val route: RouteEntity
)